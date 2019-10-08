(ns oceans-eleven.events
  (:require
   [re-frame.core :as rf]
   [ajax.core :as ajax]))

;;dispatchers

(rf/reg-event-db
 :navigate
 (fn [db [_ route]]
   (assoc db :route route)))

(rf/reg-event-db
 :common/reset-error
 (fn [db _]
   (assoc db :common/error nil)))

(rf/reg-event-fx
 :common/set-error
 (fn [cofx [_ error]]
   {:db (assoc (:db cofx) :common/error (get error :debug-message))
    :dispatch-later [{:ms 5000
                      :dispatch [:common/reset-error nil]}]}))

;;subscriptions


(rf/reg-sub
 :route
 (fn [db _]
   (-> db :route)))

(rf/reg-sub
 :page
 :<- [:route]
 (fn [route _]
   (-> route :data :name)))

(rf/reg-sub
 :common/error
 (fn [db _]
   (:common/error db)))
