(ns library.routes.books
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

(defn add-book []
  (let [all-authors (db/read-authors)
        all-publishers (db/read-publishers)]
  [:div[:h1 "Add book"]
  [:form

   [:p "Book id: " [:input {:type "text" :name "id" :id "bookId"}]]
   [:p "Book title: " [:input {:type "text" :name "name" :id "bookName"}]]
   [:p "Year of publishing: " [:input {:type "text" :name "yr_of_pub" :id "bookYearOfPublishing"}]]
   [:p "Authors:" [:select {:id "authors"}
    (for [author all-authors]
         [:option {:value (:_id author) } (:name author)])]]
   [:p "Publishers:" [:select {:id "publishers"}
    (for [publisher all-publishers]
         [:option {:value (:_id publisher) } (:name publisher)])]]

   [:p [:input {:type "submit" :value "Add book" :onclick "insertbook(authors, publishers)"}]]]]))


(defn all-books
  []
  (let [all-book (db/read-books)]
     [:div[:h1 "All books"]
     [:table.center
      [:tr [:th "Id"] [:th "Name"] [:th "Publish year"]  [:th "Author"] [:th "Publisher"] [:th "Delete book"] [:th "Update book"]]
      (for [book all-book
       :let [authId (:author_id book)
             pubId (:publisher_id book)]]
         [:tr
             [:td (:_id book)]
             [:td.editable (:name book)]
             [:td (:yr_of_pub book)]
             [:td (:name (db/find-author-by-id authId))]
             [:td (:name (db/find-publisher-by-id pubId))]
             [:td [:input.deletebook {:type "submit" :value "Delete"}]]
             [:td [:input.updatebook {:type "submit" :value "Update" }]]])]
      [:input.goback {:type "button" :value "Go back" :onclick "window.location.href = '/'"}]]))




(defn home [& [error]]
  (layout/common
   [:p error]
   (add-book)
   [:hr]
   (all-books)))

(defn save-book [id name yr_of_pub author_id publisher_id]
  (cond
    (empty? id)
    (home "Please enter id!")
    (empty? name)
    (home "Please enter name!")
    (empty? yr_of_pub)
    (home "Please enter year!")
    (empty? author_id)
    (home "Please select author!")
    (empty? publisher_id)
    (home "Please select publisher!")
  :else
    (do
      (let [id (Integer/parseInt id)
            yr_of_pub (Integer/parseInt yr_of_pub)
            author_id (Integer/parseInt author_id)
            publisher_id (Integer/parseInt publisher_id)]
        (db/insert-book id name yr_of_pub author_id publisher_id)
        (home)))))

(defn delete-book [id]
 (do
   (let [id (Integer/parseInt id)]
    (db/delete-book id))))

(defn update-book [id name yr_of_pub author_id publisher_id]
  (cond
    (nil? author_id)
    (home "Please select author!")
    (nil? publisher_id)
    (home "Please select publisher!")
  :else
   (do
    (let [id (Integer/parseInt id)
          yr_of_pub (Integer/parseInt yr_of_pub)
          author_id (Integer/parseInt author_id)
          publisher_id (Integer/parseInt publisher_id)]
      (db/update-book id name yr_of_pub author_id publisher_id)))))

(defroutes book-routes
  (GET "/book" [error] (home error))
  (POST "/save-book" [id name yr_of_pub author_id publisher_id]
                      (save-book id name yr_of_pub author_id publisher_id))
  (DELETE "/delete-book" [id] (delete-book id))
  (POST "/update-book" [id name yr_of_pub author_id publisher_id] (update-book id name yr_of_pub author_id publisher_id)))





