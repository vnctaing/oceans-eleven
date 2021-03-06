(ns oceans-eleven.core
  (:require
   [day8.re-frame.http-fx]
   [reagent.core :as r]
   [re-frame.core :as rf]
   [baking-soda.core :as b]
   [cljss.core]
   [cljss.reagent :refer-macros [defstyled]]
   [goog.events :as events]
   [goog.history.EventType :as HistoryEventType]
   [markdown.core :refer [md->html]]
   [oceans-eleven.ajax :as ajax]
   [oceans-eleven.events]
   [reitit.core :as reitit]
   [home.core :as home]
   [clojure.string :as string])
  (:import goog.History))

(defn nav-link [uri title page]
  [:a.navbar-item
   {:href  uri
    :class (when (= page @(rf/subscribe [:page])) :is-active)}
   title])

(defn navbar []
  (r/with-let [expanded? (r/atom false)]
    [:nav.navbar.is-info>div.container
     [:div.navbar-brand
      [:a.navbar-item {:href "/" :style {:font-weight :bold}} "oceans-eleven"]
      [:span.navbar-burger.burger
       {:data-target :nav-menu
        :on-click    #(swap! expanded? not)
        :class       (when @expanded? :is-active)}
       [:span] [:span] [:span]]]
     [:div#nav-menu.navbar-menu
      {:class (when @expanded? :is-active)}
      [:div.navbar-start
       [nav-link "#/" "Home" :home]
       [nav-link "#/about" "About" :about]]]]))

(defn about-page []
  [:section.section>div.container>div.content
   [:img {:src "/img/warning_clojure.png"}]])

(def pages
  {:home #'home/page})

(defn global-error []
  (if @(rf/subscribe [:common/error]) [b/Alert {:color  "danger" :is-open true
                                                :toggle #(rf/dispatch [:common/reset-error])}  @(rf/subscribe [:common/error])]))

(defn page []
  [:div
   [navbar]
   [global-error]
   [(pages @(rf/subscribe [:page]))]])

;; -------------------------
;; Routes

(def router
  (reitit/router
    [["/" :home]]))

;; -------------------------
;; History
;; must be called after routes have been defined


(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen
      HistoryEventType/NAVIGATE
      (fn [event]
        (let [uri (or (not-empty (string/replace (.-token event) #"^.*#" "")) "/")]
          (rf/dispatch
            [:navigate (reitit/match-by-path router uri)]))))
    (.setEnabled true)))

;; -------------------------
;; Initialize app
(defn mount-components []
  (rf/clear-subscription-cache!)
  (r/render [#'page] (.getElementById js/document "app")))

(defn init! []
  (rf/dispatch-sync [:navigate (reitit/match-by-name router :home)])
  (ajax/load-interceptors!)
  (hook-browser-navigation!)
  (mount-components))
