(ns oceans-eleven.routes.home
  (:require
   [oceans-eleven.layout :as layout]
   [clojure.java.io :as io]
   [oceans-eleven.middleware :as middleware]
   [oceans-eleven.db.core :as db]
   [ring.util.http-response :as response]))

(defn home-page [request]
  (layout/render request "home.html"))

(defn home-routes []
  [""
   {:middleware [;; middleware/wrap-csrf
                 middleware/wrap-formats]}
   ["/" {:get home-page}]
   ["/graphiql" {:get (fn [request] (layout/render request "graphiql.html"))}]
   ["/trip/create" {:post (fn [request] (response/ok (db/create request)))}]
   ["/trips" {:get (fn [request]  (response/ok (db/get-all request)))}]])

