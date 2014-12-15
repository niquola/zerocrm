(ns zerocrm.ctrls
  (:require [zerocrm.json :as zj]))

(defn index [{params :params}]
  {:body (zj/generate {:a "b"})
   :status 200})

(defn new-order [{params :params}]
  (pr-str params)
  {:body (zj/generate params)
   :status 200})
