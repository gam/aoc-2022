(ns gam.aoc-2022.day4
  (:require [clojure.string :as str]
            [clojure.set :as set]
            [gam.aoc :refer [->int]]
            [clojure.test :refer [deftest is testing]]))

(def demo-input (aoc/fetch-data "demo-day4.txt"))
(def real-input (aoc/fetch-data "puzzle-day4.txt"))

(defn- expand-range [range-string]
  (let [[from to] (str/split range-string #"-")]
    (range (->int from) (inc (->int to)))))

(defn make-sets [pair]
  (->> pair
      (map expand-range)
      (map set)))

(defn- has-subset? [[set1 set2]]
  (or (set/subset? set1 set2)
      (set/subset? set2 set1)))

(defn- has-overlap? [[set1 set2]]
  (seq (set/intersection set1 set2)))
      
(defn solve-1 [input]
  (->> input
       str/split-lines
       (map #(str/split % #","))
       (map make-sets)
       (map has-subset?)
       (filter identity)
       count))

(defn solve-2 [input]
  (->> input
       str/split-lines
       (map #(str/split % #","))
       (map make-sets)
       (map has-overlap?)
       (filter identity)
       count))

(deftest aoc-2021.day2
  (testing solve-1
    (is (= (solve-1 demo-input) 2))
    (is (= (solve-1 real-input) 571)))
  (testing solve-2
    (is (= (solve-2 demo-input) 4))
    (is (= (solve-2 real-input) 917))))

(clojure.test/run-tests)
