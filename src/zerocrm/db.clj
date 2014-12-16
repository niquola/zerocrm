(ns zerocrm.db
  (:require [zerocrm.tz :as zt]))

(defn uuid [] (str (java.util.UUID/randomUUID)))


(comment (def orders (atom {})))

(defn add-order [x]
  (swap! orders
         (fn [xs]
           (let [id (uuid)]
             (assoc xs id (merge x { :id id  :update (zt/now)}))))))

(defn get-order [{id :id}]
  (get @orders id))

(defn list-orders [& _]
  (vals @orders))
