(ns library.routes.publishers
   (:use compojure.core
        ring.adapter.jetty
        compojure.response
        [ring.util.response :only (response content-type)])
  (:require [compojure.core :refer :all]
            [library.views.layout :as layout]
            [hiccup.form :refer :all]
            [library.models.db :as db]
            [hiccup.page :as hic-p])
  (:import java.lang.Integer))

(extend-protocol Renderable
  com.mongodb.WriteResult
  (render [this _] (response (.toString this))))

(defn all-publishers
  []
  (let [all-pub (db/read-publishers)]
    [:div [:h1 "All publishers"]
     [:table.center
      [:tr [:th "Id"] [:th "Name"] [:th "Delete publisher"] [:th "Update publisher"]]
      (for [publisher all-pub]
         [:tr
             [:td (:_id publisher)]
             [:td.editable (:name publisher)]
             [:td [:input.deletepublisher {:type "submit" :value "Delete"}]]
             [:td [:input.updatepublisher {:type "submit" :value "Update"}]]])]
     [:input.goback {:type "button" :value "Go back" :onclick "window.location.href = '/'"}]]))


(defn add-publisher []
  [:div[:h1 "Add publisher"]
  [:form {:action "/publisher" :method "POST"}
   [:p "Publisher id: " [:input {:type "text" :name "id"}]]
   [:p "Publisher name: " [:input {:type "text" :name "name" :id "tfName"}]]
   [:p [:input {:type "submit" :value "Add publisher"}]]]])


(defn home [& [error]]
  (layout/common
   [:p error]
   (add-publisher)
   [:hr]
   (all-publishers)))

(defn save-publisher [id name]
  (cond
    (empty? id)
    (home "Please enter id!")
    (empty? name)
    (home "Please enter name!")
  :else
    (do
      (let [id (Integer/parseInt id)]
        (db/insert-publisher id name)
        (home)))))

(defn delete-publisher [id]
 (do
   (let [id (Integer/parseInt id)]
    (db/delete-publisher id))))

(defn update-publisher [id name]
  (do
    (let [id (Integer/parseInt id)]
    (db/update-publisher id name))))

(defroutes publisher-routes
  (GET "/publisher" [error] (home error))
  (POST "/publisher" [id name] (save-publisher id name))
  (DELETE "/delete-publisher" [id] (delete-publisher id))
  (POST "/update-publisher" [id name] (update-publisher id name)))





