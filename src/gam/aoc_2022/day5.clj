(ns gam.aoc-2022.day5
  (:require [clojure.string :as str]
            [gam.aoc :refer [->int fetch-data]]
            [clojure.test :refer [deftest is testing]]))

(def demo-input (fetch-data "demo-day5.txt"))
(def real-input (fetch-data "puzzle-day5.txt"))

(defn parse-line [stack-line]
  (->> stack-line
       (partition-all 4)
       (map second)))

(defn parse-stacks [stack-string]
  (->> stack-string
       (map parse-line)
       (apply map list)
       (map (partial remove #{\space}))))

(defn- parse-step [step-string]
  (let [[_ amount _ source _ sink] (str/split step-string #" ")]
    (vec (map ->int [amount source sink]))))

(defn parse-procedure [procedure-string]
  (map parse-step (str/split-lines procedure-string)))

(defn- parse-input [input]
  (let [[stack-string procedure-string] (str/split input #"\n\n") 
        stack-lines (str/split-lines stack-string)
        stacks (parse-stacks (butlast stack-lines))
        stack-ids (->> stack-lines
                       last
                       (remove #{\space})
                       (map (comp ->int str)))
        steps (parse-procedure procedure-string)]
    [(zipmap stack-ids stacks) steps]))

(defn process-step-1 [stacks [amount source sink]]
  (let [old-source (get stacks source)
        old-sink   (get stacks sink)
        payload    (take amount (get stacks source))
        new-source (nthrest old-source amount)
        new-sink   (concat (reverse payload) old-sink)]
    (assoc stacks
           sink new-sink
           source new-source)))

(defn process-step-2 [stacks [amount source sink]]
  (let [old-source (get stacks source)
        old-sink   (get stacks sink)
        payload    (take amount (get stacks source))
        new-source (nthrest old-source amount)
        new-sink   (concat payload old-sink)]
    (assoc stacks
           sink new-sink
           source new-source)))

(defn solve-1 [input]
  (->> input
       parse-input
       (apply (partial reduce process-step-1))
       sort
       vals
       (map first)
       (apply str)))

(defn solve-2 [input]
  (->> input
       parse-input
       (apply (partial reduce process-step-2))
       sort
       vals
       (map first)
       (apply str)))

(deftest aoc-2021.day5
  (testing solve-1
    (is (= (solve-1 demo-input) "CMZ"))
    (is (= (solve-1 real-input) "ZSQVCCJLL")))
  (testing solve-2
    (is (= (solve-2 demo-input) "MCD"))
    (is (= (solve-2 real-input) "QZFJRWHGS"))))

(clojure.test/run-tests)