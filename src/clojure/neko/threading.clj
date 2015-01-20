; Copyright © 2011 Sattvik Software & Technology Resources, Ltd. Co.
; All rights reserved.
;
; This program and the accompanying materials are made available under the
; terms of the Eclipse Public License v1.0 which accompanies this distribution,
; and is available at <http://www.eclipse.org/legal/epl-v10.html>.
;
; By using this software in any fashion, you are agreeing to be bound by the
; terms of this license.  You must not remove this notice, or any other, from
; this software.

(ns neko.threading
  "Utilities used to manage multiple threads on Android."
  {:author "Daniel Solano Gómez"}
  (:import android.os.Looper
           android.os.Handler))

(defn on-ui-thread?
  "Returns true if the current thread is a UI thread."
  []
  (identical? (Thread/currentThread) (.getThread (Looper/getMainLooper))))

(defn on-ui*
  "Runs the given nullary function on the UI thread.  If this function is
  called on the UI thread, it will evaluate immediately."
  [f]
  (if (on-ui-thread?)
    (f)
    (.post (Handler. (Looper/getMainLooper))
           (fn []
             (try
               (f)
               (catch Throwable err
                 (android.util.Log/e "NEKO" "Caught exception on UI thread: " err)))))))

(defmacro on-ui
  "Runs the macro body on the UI thread.  If this macro is called on the UI
  thread, it will evaluate immediately."
  [& body]
  `(on-ui* (fn [] ~@body)))
