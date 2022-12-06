(ns gam.aoc-2022.day6
  (:require [gam.aoc :refer [fetch-data]]
            [clojure.test :refer [deftest is testing]]))

(def examples ["mjqjpqmgbljsphdztnvjfqwrcgsmlb"
               "bvwbjplbgvbhsrlpgdmjqwftvncz"
               "nppdvjthqldpwncqszvftbrmjlhg"
               "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"
               "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"])

(def demo-input (first examples))
(def real-input (fetch-data "puzzle-day6.txt"))

(defn find-marker [marker-length input]
  (loop [idx 0
         s input]
    (if (= marker-length (count (set (take marker-length s))))
      (+ marker-length idx)
      (recur (inc idx) (rest s)))))

(defn solve-1 [input]
  (find-marker 4 input))

(defn solve-2 [input]
  (find-marker 14 input))

(deftest aoc-2021.day6
  (testing solve-1
    (is (= (solve-1 demo-input) 7))
    (is (= (solve-1 real-input) 1275)))
  (testing solve-2
    (is (= (solve-2 demo-input) 19))
    (is (= (solve-2 real-input) 3605))))

(clojure.test/run-tests)
