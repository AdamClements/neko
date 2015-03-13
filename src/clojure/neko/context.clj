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

(ns neko.context
  "Utilities to aid in working with a context."
  {:author "Daniel Solano Gómez"}
  (:import android.content.Context))

(def ^{:doc "Stores Application instance that acts as context."}
  ^Context context)

(defn init
  "Saves the application context in neko.context/context"
  [^Context context]
  (alter-var-root #'context (fn [& _] (.getApplicationContext context))))
