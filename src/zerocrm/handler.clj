(ns zerocrm.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [org.httpkit.server :as ohs]
            [hiccup.core :as hc]
            [zerocrm.ctrls :as zc]
            [zerocrm.json :as zj]
            [compojure.route :as route]))

(defroutes app-routes
  (GET  "/data"   [] #'zc/index)
  (POST "/orders" [] #'zc/new-order)
  (route/resources "/")
  (route/not-found "Not Found"))

(defn json-request-wrp [h]
  (fn [req]
    (if-let [b (and (:body req) (slurp (:body req)))]
      (h (assoc req :params (merge (or (:params req)) (zj/parse b))))
      (h req))))

(def app
  (-> #'app-routes
      (json-request-wrp)
      (handler/site)))

(defn start []
  (def stop
    (ohs/run-server #'app {:port 8080})))

(comment
  (require '[vinyasa.pull :as vp])
  (vp/pull 'http-kit)
  (vp/pull 'hiccup)
  (vp/pull 'garden)
  (start)
  (stop))
