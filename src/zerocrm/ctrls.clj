(ns zerocrm.ctrls
  (:require [zerocrm.json :as zj]
            [zerocrm.db :as zd]))

(defn index [{params :params}]
  {:body (zj/generate {:a "b"})
   :status 200})

(defn new-order [{params :params}]
  (let [ord (zd/add-order params)]
    {:body (zj/generate ord)
     :status 200}))

(defn list-orders [{params :params}]
  (let [ords (zd/list-orders params)]
    {:body (zj/generate ords)
     :status 200}))

(defn get-order [{params :params}]
  (let [ord (zd/get-order params)]
    {:body (zj/generate ord)
     :status 200}))
