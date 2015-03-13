(defproject com.swiftkey.clarity/neko-lite "1.0.1-SNAPSHOT"
  :description "Neko-lite is a set of utilities for compilation and repl support on clojure-android"
  :url "https://github.com/AdamClements/neko"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure-android/clojure "1.5.1-jb"]]
  :source-paths ["src" "src/clojure"]
  :java-source-paths ["src/java" "gen"]

  :profiles {:default [:user :dev :android-common]}
  :plugins [[lein-droid "0.3.0"]]

  :android {:library true
            :target-version :jelly-bean})
