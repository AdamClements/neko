(ns neko.tools.repl
  (:import android.content.Context
           java.util.concurrent.atomic.AtomicLong
           java.util.concurrent.ThreadFactory
           java.io.FileNotFoundException))

(defn android-thread-factory
  "Returns a new ThreadFactory with increased stack size. It is used to
  substitute nREPL's native `configure-thread-factory` on Android platform."
  []
  (let [counter (AtomicLong. 0)]
    (reify ThreadFactory
      (newThread [_ runnable]
        (doto (Thread. (.getThreadGroup (Thread/currentThread))
                       runnable
                       (format "nREPL-worker-%s" (.getAndIncrement counter))
                       1048576) ;; Hardcoded stack size of 1Mb
          (.setDaemon true))))))

(defn start-repl
  "Starts a remote nREPL server. Creates a `user` namespace because nREPL
  expects it to be there while initializing. References nrepl's `start-server`
  function on demand because the project can be compiled without nrepl
  dependency."
  [& repl-args]
  (binding [*ns* (create-ns 'user)]
    (refer-clojure)
    (require '[clojure.tools.nrepl.server :as nrepl])
    (require '[clojure.tools.nrepl.middleware.interruptible-eval :as ie])
    (with-redefs-fn {(resolve 'ie/configure-thread-factory)
                     android-thread-factory}
      #(apply (resolve 'nrepl/start-server)
              :handler ((resolve 'nrepl/default-handler))
              repl-args))))
