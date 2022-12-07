(ns gam.aoc-2022.day7
  (:require [clojure.string :as str]
            [gam.aoc :refer [->int fetch-data]]
            [clojure.test :refer [deftest is testing]]))

(def demo-input (fetch-data "demo-day7.txt"))
(def real-input (fetch-data "puzzle-day7.txt"))

(defn- parse-input [input]
  (map (fn [e]
         [(first e) (rest e)])
       (map #(str/split % #"\n")
            (next (str/split input #"\$ ")))))

(defn process-entry [entry]
  (let [[data name] (str/split entry #" ")]
    (if (= data "dir")
      {:type :dir
       :value {name {:files []
                     :dirs []}}}
      {:type :file
       :value {name (->int data)}})))


(defn- process-dircontent [strings dirmap]
  (let [contents (->> strings
                      (map process-entry)
                      (group-by :type))
        dirs (->> contents
                  :dir
                  (map :value)
                  (apply merge))
        files (->> contents
                   :file
                   (map :value)
                   (apply merge))]
    (assoc dirmap :dirs dirs :files files)))

(defn reducer-function [[tree location] [command result]]
  (cond
    (= command "cd /") [{"/" {:dirs {} :files {}}} ["/"]]
    (= command "cd ..") [tree (pop (pop location))]
    (= command "ls") (let [dircontent (process-dircontent
                                       result
                                       (get-in tree location))]
                       [(assoc-in tree location dircontent) location])
    :else [tree (conj location
                      :dirs
                      (second (str/split command #" ")))]))

(defn build-tree [input]
  (->> input
       parse-input
       (reduce reducer-function [])
       first))

(defn find-size [[_ directory]]
  (let [file-size (->> directory
                       :files
                       vals
                       (apply +))
        subdir-sizes  (map find-size (:dirs directory))
        my-subdir-size (->> subdir-sizes
                            (map first)
                            (apply +))
        my-total-size (+ file-size my-subdir-size)]
    [my-total-size (conj (mapcat second subdir-sizes) my-total-size)]))

(defn solve-1 [input]
  (->> input
       build-tree
       (apply find-size)
       second
       (filter (partial > 100000))
       (apply +)))

(defn solve-2 [input]
  (let [sizes (->> input
                   build-tree
                   (apply find-size)
                   second
                   (sort >))
        needed (- 30000000 (- 70000000 (first sizes)))
        big-enough (filter (partial <= needed) sizes)]
    (last big-enough)))

(comment 
  ;;Example tree:
  {"/" {:dirs {"a" {:dirs {"e" {:dirs {}
                                :files {"i" 584}}}
                    :files {"f" 29116
                            "g" 2557
                            "h.lst" 62596}}
               "d" {:dirs {}
                    :files {"j" 4060174
                            "d.log" 8033020
                            "d.ext" 5626152
                            "k" 7214296}}}
        :files {"b.txt" 14848514
                "c.dat" 8504156}}}
)

(deftest aoc-2021.day7
  (testing solve-1
    (is (= (solve-1 demo-input) 95437))
    (is (= (solve-1 real-input) 1243729)))
  (testing solve-2
    (is (= (solve-2 demo-input) 24933642))
    (is (= (solve-2 real-input) 4443914))))

(clojure.test/run-tests)