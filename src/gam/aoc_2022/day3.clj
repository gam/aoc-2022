(ns gam.aoc-2022.day3
  (:require [clojure.string :as str]
            [clojure.set :as set]
            [gam.aoc :as aoc]
            [clojure.test :refer [deftest is testing]]))


(def demo-input (aoc/fetch-data "demo-day3.txt"))
(def real-input (aoc/fetch-data "puzzle-day3.txt"))

(def letters (concat (map char (range 97 123))
                     (map char (range 65 91))))
(def values (zipmap letters (range 1 53)))

(defn- find-common-entries [item-strings]
  (->> item-strings
       (map set)
       (apply set/intersection)
       first))

(defn- find-in-both-compartments [backpack]
  (let [midpoint (/ (count backpack) 2)]
    (->> backpack
         (split-at midpoint)
         find-common-entries)))

(defn- find-elf-groups [elf-strings]
  (partition 3 elf-strings))

(defn solve-1 [input]
  (->> (str/split-lines input)
       (map find-in-both-compartments)
       (map values)
       (apply +)))

(defn solve-2 [input]
  (->> (str/split-lines input)
       find-elf-groups
       (map find-common-entries)
       (map values)
       (apply +)))

(deftest aoc-2021.day3
  (testing solve-1
    (is (= (solve-1 demo-input) 157))
    (is (= (solve-1 real-input) 7581)))
  (testing solve-2
    (is (= (solve-2 demo-input) 70))
    (is (= (solve-2 real-input) 2525))))

(clojure.test/run-tests)
