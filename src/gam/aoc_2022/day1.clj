(ns gam.aoc-2022.day1
  (:require [clojure.string :as str]
            [gam.aoc :as aoc]
            [clojure.test :refer [deftest is testing]]))

(def demo-input (aoc/fetch-data "demo-day1.txt"))
(def real-input (aoc/fetch-data "puzzle-day1.txt"))

(defn ->elves [input]
  (str/split input #"\n\n"))

(defn parse-elf [elf]
  (->> elf
       str/split-lines
       (map aoc/->int)
       (apply +)))

(defn solve-1 [input]
  (->> input
       ->elves
       (map parse-elf)
       (sort >)
       first))

(defn solve-2 [input]
  (->> input
       ->elves
       (map parse-elf)
       (sort >)
       (take 3)
       (apply +)))

(deftest aoc-2021.day1
  (testing solve-1
    (is (= (solve-1 demo-input) 24000))
    (is (= (solve-1 real-input) 69289)))
  (testing solve-2
    (is (= (solve-2 demo-input) 45000))
    (is (= (solve-2 real-input) 205615))))

(clojure.test/run-tests)