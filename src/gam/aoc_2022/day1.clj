(ns gam.aoc-2022.day1
  (:require [clojure.string :as str]
            [gam.aoc :as aoc]
            [clojure.test :refer [deftest is testing]]))


;; Evaluate the following expression to open today's puzzle.
#_(aoc/open-puzzle-page 1)

;; Evaluate the following expression to retrieve and store today's puzzle input.
#_(aoc/download-puzzle-input 1)

(def demo-input (aoc/fetch-data "demo-day1.txt"))
(def real-input (aoc/fetch-data "puzzle-day1.txt"))
(apply + 
       (take 3 
             (sort > 
                   (map (comp #(apply + %) #(map aoc/->int %) str/split-lines) 
                        (str/split real-input #"\n\n"))))))


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
    (is (= (solve-1 real-input) 45000)))
  (testing solve-2
    (is (= (solve-2 demo-input) 45000))
    (is (= (solve-2 real-input) 1248))))

(clojure.test/run-tests)

#_demo-input
(parse-elf "1000\n2000\n3000")

#_(solve-2 demo-input)

(aoc/->int "1000")
#_elves

