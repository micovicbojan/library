(ns library.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [hiccup.middleware :refer [wrap-base-url]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [library.routes.home :refer [home-routes]]
            [library.routes.authors :refer [author-routes]]
            [library.routes.publishers :refer [publisher-routes]]
            [library.routes.books :refer [book-routes]]))



(defn init []
  (println "library is starting"))

(defn destroy []
  (println "library is shutting down"))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (routes author-routes publisher-routes book-routes home-routes app-routes)
      (handler/site)
      (wrap-base-url)))


