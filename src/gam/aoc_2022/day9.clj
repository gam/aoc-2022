(ns gam.aoc-2022.day9
  (:require [clojure.string :as str]
            [gam.aoc :refer [->int fetch-data]]
            [clojure.test :refer [deftest is testing]]))

(def demo-input-1 (fetch-data "demo-day9-1.txt"))
(def demo-input-2 (fetch-data "demo-day9-2.txt"))
(def real-input (fetch-data "puzzle-day9.txt"))

(def delta-mapping {"R" [1 0]
                    "U" [0 1]
                    "L" [-1 0]
                    "D" [0 -1]})

(defn parse-move [motion]
  (let [[direction amount] (str/split motion #" ")]
    (repeat (->int amount) (delta-mapping direction))))

(defn parse-input [input]
  (->> input
       str/split-lines
       (map parse-move)
       (apply concat)
       vec))

(defn- adjacents [[x y]]
  (set (for [dx [-1 0 1]
             dy [-1 0 1]]
         [(+ dx x) (+ dy y)])))

(defn- adjacent?
  "An adjacent position is either identical or a neighbour;
   i.e. the position is next to the other, either horisontally,
   vertically or diagonally."
  [pos1 pos2]
  (contains? (adjacents pos1) pos2))

(defn- move-knot [[head-x head-y] [knot-x knot-y]]
  (cond
    (= head-x knot-x) [knot-x (if (> head-y knot-y) (inc knot-y) (dec knot-y))]
    (= head-y knot-y) [(if (> head-x knot-x) (inc knot-x) (dec knot-x)) knot-y]
    :else (let [new-x (if (> head-x knot-x)
                        (inc knot-x)
                        (dec knot-x))
                new-y (if (> head-y knot-y)
                        (inc knot-y)
                        (dec knot-y))]
            [new-x new-y])))

(defn handle-move-1 [{:keys [visited head tail]} [delta-x delta-y]]
  (let [[x y] head
        new-head [(+ x delta-x)
                  (+ y delta-y)]
        new-tail (if (adjacent? new-head tail)
                   tail
                   (move-knot new-head tail))]
    {:head new-head
     :tail new-tail
     :visited (conj visited new-tail)}))

(defn update-next-knot [{:keys [knots head]} tail]
  (let [new-knot (if (adjacent? head tail)
                   tail
                   (move-knot head tail))]
    {:head new-knot
     :knots (conj knots new-knot)}))

(defn- update-knots [head knots]
  (->> knots
       (reduce update-next-knot {:knots [] :head head})
       :knots))

(defn handle-move-2 [{:keys [visited head knots]} [delta-x delta-y]]
  (let [[x y] head
        new-head [(+ x delta-x)
                  (+ y delta-y)]
        new-knots (update-knots new-head knots)]
    {:head new-head
     :knots new-knots
     :visited (conj visited (last new-knots))}))

(defn solve-1 [input]
  (->> input
       parse-input
       (reduce handle-move-1 {:visited #{[0 0]}
                              :head [0 0]
                              :tail [0 0]})
       :visited
       count))

(defn solve-2 [input]
  (->> input
       parse-input
       (reduce handle-move-2 {:visited #{[0 0]}
                              :head [0 0]
                              :knots (repeat 9 [0 0])})
       :visited
       count))

(deftest aoc-2021.day8
  (testing solve-1
    (is (= (solve-1 demo-input-1) 13))
    (is (= (solve-1 real-input) 6563)))
  (testing solve-2
    (is (= (solve-2 demo-input-2) 36))
    (is (= (solve-2 real-input) 2653))))

(clojure.test/run-tests)