(ns library.routes.home
  (:require [compojure.core :refer :all]
            [library.views.layout :as layout]
            [hiccup.form :refer :all]
            [library.models.db :as db]
            [hiccup.page :as hic-p]))


(defn home []
(layout/common
 [:h1.h1 "Library"]
 [:p "Please select entity to work with:"]
 [:input.pageBtn {:type "a" :value "Authors" :onclick "window.location.href = '/author'"}]
 [:input.pageBtn {:type "a" :value "Publishers" :onclick "window.location.href = '/publisher'"}]
 [:input.pageBtn {:type "a" :value "Books" :onclick "window.location.href = '/book'"}]))


(defroutes home-routes
  (GET "/" [] (home)))





