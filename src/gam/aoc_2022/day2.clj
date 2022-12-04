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

(def result-value
  {:win 6
   :draw 3
   :loss 0})

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

(defn game-result
  "Determine if the given combination of moves
   results in a `:win`, a `:draw` or a `:loss` 
   for the one making `move2`."
  [move1 move2]
  (cond
    (= move1 move2) :draw
    (= (winning-moves move1) move2) :win
    :else :loss))

(defn game-score
  "Calculate the score for a single game, 
   given two moves, each of which is either
   `:rock`, `:paper` or `:scissors`"
  [[move1 move2]]
  (+ (result-value (game-result move1 move2))
     (move-value move2)))

(defn convert-moves-1 
  "Translate the codes for a game to the 
   moves they represent."
  [moves]
  (map code->move moves))

(defn convert-moves-2 
  "Translate the codes for a game to the opponent move 
   and game outcome they represent, returning the 
   corresponding moves."
  [[code1 code2]]
  (let [move1 (code->move code1)
        move2 (condp = (code->outcome code2)
                :draw move1
                :win (winning-moves move1)
                :loss (losing-moves move1))]
    [move1 move2]))

(defn- parse-input [input]
  (->> input
       str/split-lines
       (map #(str/split % #" "))))

(defn solve-1 [input]
  (->> (parse-input input)
       (map convert-moves-1)
       (map game-score)
       (apply +)))

(defn solve-2 [input]
  (->> (parse-input input)
       (map convert-moves-2)
       (map game-score)
       (apply +)))

(deftest aoc-2021.day2
  (testing solve-1
    (is (= (solve-1 demo-input) 15))
    (is (= (solve-1 real-input) 11666)))
  (testing solve-2
    (is (= (solve-2 demo-input) 12))
    (is (= (solve-2 real-input) 12767))))

(clojure.test/run-tests)
