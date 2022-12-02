(ns gam.aoc-2022.day2
  (:require [clojure.string :as str]
            [clojure.set :as set]
            [gam.aoc :as aoc]
            [clojure.test :refer [deftest is testing]]))

(def demo-input (aoc/fetch-data "demo-day2.txt"))
(def real-input (aoc/fetch-data "puzzle-day2.txt"))

(def move-value
  {:rock 1
   :paper 2
   :scissors 3})

(def code->move
  {"A" :rock
   "B" :paper
   "C" :scissors
   "X" :rock
   "Y" :paper
   "Z" :scissors})

(def code->outcome
  {"X" :loss
   "Y" :draw
   "Z" :win})

(def winning-moves
  {:rock     :paper
   :paper    :scissors
   :scissors :rock})

(def losing-moves (set/map-invert winning-moves))

(def result-value
  {:win 6
   :draw 3
   :loss 0})

(defn game-result [move1 move2]
  (cond
    (= move1 move2) :draw
    (= (winning-moves move1) move2) :win
    :else :loss))

(defn game-score-1 [[p1 p2]]
  (let [move1 (code->move p1)
        move2 (code->move p2)]
    (+ (result-value (game-result move1 move2))
       (move-value move2))))

(defn decode-move [p1 p2]
  (let [move (code->move p1)
        outcome (code->outcome p2)]
    (condp = outcome
      :draw move
      :win (winning-moves move)
      :loss (losing-moves move))))

(defn game-score-2 [[p1 p2]]
  (let [move1 (code->move p1)
        move2 (decode-move p1 p2)]
    (+ (result-value (game-result move1 move2))
       (move-value move2))))

(defn solve-1 [input]
  (->> input
       str/split-lines
       (map #(str/split % #" "))
       (map game-score-1)
       (apply +)))

(defn solve-2 [input]
  (->> input
       str/split-lines
       (map #(str/split % #" "))
       (map game-score-2)
       (apply +)))


(deftest aoc-2021.day2
  (testing solve-1
    (is (= (solve-1 demo-input) 15))
    (is (= (solve-1 real-input) 11666)))
  (testing solve-2
    (is (= (solve-2 demo-input) 12))
    (is (= (solve-2 real-input) 12767))))

(clojure.test/run-tests)
