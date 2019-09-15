(ns oceans-eleven.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[oceans-eleven started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[oceans-eleven has shut down successfully]=-"))
   :middleware identity})
