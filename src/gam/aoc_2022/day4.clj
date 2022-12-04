(ns gam.aoc-2022.day4
  (:require [clojure.string :as str]
            [clojure.set :as set]
            [gam.aoc :refer [->int]]
            [clojure.test :refer [deftest is testing]]))

(def demo-input (aoc/fetch-data "demo-day4.txt"))
(def real-input (aoc/fetch-data "puzzle-day4.txt"))

(defn- expand-range 
  "Take an incoming string representing a range, e.g. `2-4`,
   and return the corresponding sequence of characters from 
   the first to the last value (inclusive)."
  [range-string]
  (let [[from to] (str/split range-string #"-")]
    (range (->int from) (inc (->int to)))))

(defn- make-sets 
  "Take a pair of type `[\"2-4\" \"3-7\"]`, expand each
   range string to the corresponding sequence of integers,
   and convert the result to a set. 
   Return the sequence of sets.
   In the above example, the result should be:
   ```
   (#{2 3 4} #{3 4 5 6 7})
   ```"
  [pair]
  (->> pair
      (map expand-range)
      (map set)))

(defn- has-subset? [[set1 set2]]
  (or (set/subset? set1 set2)
      (set/subset? set2 set1)))

(defn- has-overlap?
  "Determine whether there is overlap between the pair of sets provided. 
   Is not a true predicate, due to using the Clojure idiom of using
   `(seq coll)` to determine if `coll` is empty."
  [[set1 set2]]
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
