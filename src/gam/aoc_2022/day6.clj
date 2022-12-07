(ns gam.aoc-2022.day6
  (:require [gam.aoc :refer [fetch-data]]
            [clojure.test :refer [deftest is testing]]))

(def demo-input "mjqjpqmgbljsphdztnvjfqwrcgsmlb")
(def real-input (fetch-data "puzzle-day6.txt"))

#_(defn find-marker [n input]
    (loop [idx 0
           s input]
      (if (apply distinct? (take n s))
        (+ n idx)
        (recur (inc idx) (rest s)))))

(defn find-marker [length input]
  (letfn [(check-candidate [idx candidate]
                           (when (apply distinct? candidate)
                             (+ idx length)))]
    (->> input
         (partition length 1)
         (keep-indexed check-candidate)
         first)))

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