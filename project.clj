(defproject zerocrm "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.3.1"]
                 [ring/ring-defaults "0.1.2"]
                 [org.clojure/java.jdbc "0.3.3"]
                 [cheshire "5.3.1"]
                 [clj-time "0.6.0"]
                 [http-kit "2.1.16"]
                 [ring-mock "0.1.5"]]
  :plugins [[lein-ancient "0.5.5"]]
  :profiles
  {:dev {:dependencies
         [[im.chit/vinyasa "0.2.2"]
          [javax.servlet/servlet-api "2.5"] ]}})
