(ns zerocrm.tz
    (:require  [clj-time.core :as t]))

(def app-zone  (t/time-zone-for-offset 3))
(def db-zone  (t/time-zone-for-id "UTC"))

(defn now  []
    (t/to-time-zone  (t/now) app-zone))
