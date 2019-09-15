(ns oceans-eleven.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [oceans-eleven.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[oceans-eleven started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[oceans-eleven has shut down successfully]=-"))
   :middleware wrap-dev})
