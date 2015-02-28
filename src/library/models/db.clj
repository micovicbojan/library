(ns library.models.db
(:require [monger.core :as mg]
          [monger.collection :as mc])
(:import [com.mongodb MongoOptions ServerAddress]))

(def conn (mg/connect))

(def db
    (mg/get-db conn "library"))

;;(let [conn (mg/connect)
  ;;    db   (mg/get-db conn "library")])

;;author
(defn insert-author [id name]
  (try
    (mc/insert db :author
                {:_id id
                 :name name})
  (catch com.mongodb.MongoException$DuplicateKey e (prn "Duplicate key exception cought!"))))

(defn update-author [id name]
  (mc/update-by-id db :author id
               {:name name}))

(defn delete-author [id]
 (mc/remove-by-id db :author id))

(defn read-authors []
 (mc/find-maps db :author))


;;publisher
(defn insert-publisher [id name]
  (try
    (mc/insert db :publisher
                {:_id id
                 :name name})
  (catch com.mongodb.MongoException$DuplicateKey e (prn "Duplicate key exception cought!"))))

(defn update-publisher [id name]
  (mc/update-by-id db :publisher id
               {:name name}))

(defn delete-publisher [id]
 (mc/remove-by-id db :publisher id))

(defn read-publishers []
 (mc/find-maps db :publisher))

;;book
(defn insert-book [id name yr_of_pub author_id publisher_id]
  (try
    (mc/insert db :book
                {:_id id
                 :name name
                 :yr_of_pub yr_of_pub
                 :author_id author_id
                 :publisher_id publisher_id})
  (catch com.mongodb.MongoException$DuplicateKey e (prn "Duplicate key exception cought!"))))

(defn update-book [id name yr_of_pub author_id publisher_id]
  (mc/update-by-id db :book id
                {:name name
                 :yr_of_pub yr_of_pub
                 :author_id author_id
                 :publisher_id publisher_id}))

(defn delete-book [id]
 (mc/remove-by-id db :book id))

(defn read-books []
 (mc/find-maps db :book))

(defn find-publisher-by-id [id]
  (mc/find-map-by-id db :publisher id))

(defn find-author-by-id [id]
  (mc/find-map-by-id db :author id))



















