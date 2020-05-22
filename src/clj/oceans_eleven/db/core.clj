(ns oceans-eleven.db.core
  (:require
   [datomic.client.api :as d]
   [io.rkn.conformity :as c]
   [mount.core :refer [defstate]]
   [clojure.pprint :as p]
   [ring.util.request]
   [oceans-eleven.config :refer [env]]))

(def cfg {:server-type        :peer-server
          :access-key         "myaccesskey"
          :secret             "mysecret"
          :endpoint           "localhost:8998"
          :validate-hostnames false})

(def client (d/client cfg))

(def conn (d/connect client {:db-name "pensine"}))

(def o11-schema [{:db/ident       :trip/name
                  :db/valueType   :db.type/string
                  :db/cardinality :db.cardinality/one}])

(defn create [e]
  (let [body-str  (ring.util.request/body-string e)
        trip-name (:params e)]
    (d/transact conn {:tx-data [{:trip/name (:name trip-name)}]})))

(def all-trips '[:find ?e
                 :where [_ :trip/name ?e]])
(defn get-all [e]
  (d/q {:query all-trips :args [ (d/db conn) ]}))
