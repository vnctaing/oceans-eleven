(ns home.events
  (:require
   [day8.re-frame.http-fx]
   [re-frame.core :as rf]
   [ajax.core :as ajax]))

(rf/reg-event-db
 :say-hi
 (fn [_ [_ _]]
      (js/console.log "hi")))

(rf/reg-event-fx
 :create-trip
 (fn [_ [_ a]]
   {:http-xhrio {:method :post
                 :uri "http://localhost:3000/trip/create"
                 :timeout 5000
                 :format          (ajax/json-request-format)
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success [:say-hi]
                 :on-failure [:say-hi]
                 }}))
