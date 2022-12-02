(ns gam.aoc
  (:require [clojure.java.io :as io]
            [clj-http.client :as client]
            [clojure.java.browse :refer [browse-url]]
            [clojure.string :as str]))

(def ^:private ^:dynamic *cookie*
  (let [filepath (str (System/getProperty "user.home") "/.clojure/.advent-of-code_session_cookie")]
    (->> filepath
         slurp
         str/trim-newline)))

(defn open-puzzle-page [day]
  (browse-url (format "https://adventofcode.com/2022/day/%d" day)))

(defn download-puzzle-input [day]
  (let [url (format "https://adventofcode.com/2022/day/%d/input" day)
        headers {:headers {"Cookie" *cookie*}}
        filename (format "resources/puzzle-day%d.txt" day)]
    (->> (client/get url headers)
         :body
         (spit filename))))

(defn fetch-data [filename]
  (-> filename
      io/resource
      slurp))

(defn fetch-input [day type]
  (let [filename (format "%s-day%d.txt" (name type) day)]
    (slurp (io/resource filename))))

(defn ->int [s]
  (Integer/parseInt s))