(ns home.events
  (:require
   [day8.re-frame.http-fx]
   [re-frame.core :as rf]
   [ajax.core :as ajax]))

(rf/reg-event-fx
  :create-trip
  (fn [_ [_ name]]
    {:http-xhrio {:method          :post
                  :uri             "http://localhost:3000/trip/create"
                  :params          {:name name}
                  :timeout         5000
                  :format          (ajax/json-request-format)
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success      []
                  :on-failure      [:common/set-error]}}))

(rf/reg-event-fx
  :get-trips
  (fn [_ _]
    {:http-xhrio {:method          :get
                  :uri             "http://localhost:3000/trips"
                  :timeout         5000
                  :response-format (ajax/json-response-format {:keywrds? true})
                  :on-success      [:common/set-error]
                  :on-failure      [:common/set-error]}}))
