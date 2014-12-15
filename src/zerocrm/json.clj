(ns zerocrm.json
  (:require
    [clj-time.format :as tfmt]
    [clj-time.core :as t]
    [clj-time.coerce :as tcoer]
    [zerocrm.tz :as zt]
    [cheshire.core :as cc]
    [cheshire.generate :as cg]
    [clojure.string :as string]
    [clojure.walk :as wlk]))

(declare coerce-dates-in-json)

(defn parse  [str]
  (coerce-dates-in-json
    (cc/parse-string str keyword)))

(defn generate  [clj &  [options]]
  (cc/generate-string clj options))

(def date-to-json-formatter  (tfmt/with-zone
                               (tfmt/formatters :date-time)
                               zt/app-zone))

(cg/add-encoder
  org.joda.time.DateTime
  (fn  [d json-generator]
    (.writeString json-generator
                  (tfmt/unparse date-to-json-formatter d))))

(cg/add-encoder
  java.io.File
  (fn  [f json-generator]
    (.writeString json-generator
                  (pr-str f))))

(defn- coerce-dates-in-json  [json]
  "Converts datetimes in JSON objects
  from UTC to local application zone."
  (wlk/prewalk
    (fn  [v]
      (if  (string? v)
        (let  [format  (condp re-matches v
                         #"^\d\d\d\d-\d\d-\d\dT\d\d:\d\d:\d\d\.\d+Z$" :date-time
                         #"^\d\d\d\d-\d\d-\d\dT\d\d:\d\d:\d\dZ$" :date-time-no-ms
                         nil)]
          (if format
            (t/to-time-zone  (tfmt/parse  (tfmt/formatters format) v)
                            zt/app-zone)
            v))
        v))
    json))
