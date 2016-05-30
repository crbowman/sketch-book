(defproject sketch-book "0.1.0-SNAPSHOT"
  :description "A collection of sketches using the Quil clojure wrapper for Processing."
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/data.json "0.2.3"]
                 ;; [org.clojars.automata/ddf.minim "2.1.0"]
                 [nio "1.0.2"]
                 [gloss "0.2.2"]
                 [clj-time "0.6.0"]
                 [quil "2.4.0"]]
  :jvm-opts ["-server" "-Xmx8g"])
