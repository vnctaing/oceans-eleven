(ns home.core
  (:require
   [reagent.core :as r]
   [baking-soda.core :as b]))

(defonce squad-name (r/atom ""))

(defn create-squad [e]
  (reset! squad-name ""))

(defn create-squad-form [{:keys [value on-save]}]
  [:form
   [b/FormGroup
    [b/Label "Where are you going ?"]
    [b/InputGroup
     [b/Input {:placeholder "Squad" :value @value :on-change #(reset! value (-> % .-target .-value))}]
     [b/InputGroupAddon {:addon-type "append"} [b/Button {:type "button" :color "success"} "Create trip"]]]]])

(defn page []
  [:div "Ocean's eleven"
   [create-squad-form {:value squad-name :on-save create-squad}]])

