(ns zerocrm.auth
  (:require [zerocrm.json :as zj]))

(defn uuid [] (str (java.util.UUID/randomUUID)))

(def sess (atom {}))

(defn current-user-wrp [h]
  (fn [{{sid :sid} :session :as req}]
    (println "Wrap user" sid (get @sess sid) @sess)
    (if-let [usr (get @sess sid)]
      (do
        (println "Session:" usr)
        (h (assoc req :user usr)))
      (h (update-in req [:session :sid] (constantly nil))))))

(defn sign-in [{params :params}]
  (let [sid (str (uuid))]
    (swap! sess assoc sid params)
    {:session {:sid sid}
     :body (zj/generate params)}))

(defn user [{user :user}]
  (if user
    {:body (zj/generate user)}
    {:status 401 :body "You are not signed in"}))

(defn sign-out [{{sid :sid} :session}]
  (swap! sess dissoc sid))
