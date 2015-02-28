(ns library.routes.authors
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

(defn all-authors
  []
  (let [all-auth (db/read-authors)]
     [:div[:h1 "All authors"]
     [:table.center
      [:tr [:th "Id"] [:th "Name"] [:th "Delete author"] [:th "Update author"]]
      (for [auth all-auth]
         [:tr
             [:td (:_id auth)]
             [:td.editable (:name auth)]
             [:td [:input.deleteauthor {:type "submit" :value "Delete"}]]
             [:td [:input.updateauthor {:type "submit" :value "Update"}]]])]
      [:input.goback {:type "button" :value "Go back" :onclick "window.location.href = '/'"}]]))


(defn add-author []
  [:div [:h1 "Add author"]
  [:form {:action "/author" :method "POST"}
   [:p "Author id: " [:input {:type "text" :name "id"}]]
   [:p "Author name: " [:input {:type "text" :name "name" :id "tfName"}]]
   [:p [:input {:type "submit" :value "Add author"}]]]])


(defn home [& [error]]
  (layout/common
   [:p error]
   (add-author)
   [:hr]
   (all-authors)))

(defn save-author [id name]
  (cond
    (empty? id)
    (home "Please enter id!")
    (empty? name)
    (home "Please enter name!")
  :else
    (do
      (let [id (Integer/parseInt id)]
        (db/insert-author id name)
        (home)))))

(defn delete-author [id]
 (do
   (let [id (Integer/parseInt id)]
    (db/delete-author id))))

(defn update-author [id name]
  (do
    (let [id (Integer/parseInt id)]
    (db/update-author id name))))

(defroutes author-routes
  (GET "/author" [error] (home error))
  (POST "/author" [id name] (save-author id name))
  (DELETE "/delete-author" [id] (delete-author id))
  (POST "/update-author" [id name] (update-author id name)))





