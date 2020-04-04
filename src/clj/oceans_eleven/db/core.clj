(ns oceans-eleven.db.core
  (:require
   [datomic.client.api :as d]
   [io.rkn.conformity :as c]
   [mount.core :refer [defstate]]
   [oceans-eleven.config :refer [env]]))


(defn install-schema
  "This function expected to be called at system start up.

  Datomic schema migraitons or db preinstalled data can be put into 'migrations/schema.edn'
  Every txes will be executed exactly once no matter how many times system restart."
  [conn]
  (let [norms-map (c/read-resource "migrations/schema.edn")]
    (c/ensure-conforms conn norms-map (keys norms-map))))

(def cfg {:server-type        :peer-server
          :access-key         "myaccesskey"
          :secret             "mysecret"
          :endpoint           "localhost:8998"
          :validate-hostnames false})
(def client (d/client cfg))

(def conn (d/connect client {:db-name "pensine"}))

(def o11-schema [
                 {:db/ident       :trip/name
                  :db/valueType   :db.type/string
                  :db/cardinality :db.cardinality/one}]
  )

(d/transact conn {:tx-data o11-schema })
