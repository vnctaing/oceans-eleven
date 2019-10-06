(ns oceans-eleven.routes.home
  (:require
    [oceans-eleven.layout :as layout]
    [clojure.java.io :as io]
    [oceans-eleven.middleware :as middleware]
    [ring.util.http-response :as response]))

(defn home-page [request]
  (layout/render request "home.html"))

(defn home-routes []
  [""
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}
   ["/" {:get home-page}]
   ["/graphiql" {:get (fn [request] (layout/render request "graphiql.html"))}]
   ["/trip/create" {:post (fn [request] (response/ok))}]
   ["/docs" {:get (fn [_]
                    (-> (response/ok (-> "docs/docs.md" io/resource slurp))
                        (response/header "Content-Type" "text/plain; charset=utf-8")))}]])

