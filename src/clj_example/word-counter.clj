(ns clj-example.word-counter
  (:require [clojure.string :as str]))

;; TODOS. Make this program faster.
;; Right now takes 1539.14124 msecs.

(defn read-file [path]
  ;; Read file and return as sequence
  (with-open [rdr (clojure.java.io/reader path)]
    (reduce conj [] (line-seq rdr))))


(defn clean-line
  ;; Sanitize line.
  ;; Remove spaces & convert words to lower case.
  [line]
  ((comp #(str/split % #"\s+") str/lower-case clojure.string/trim) line))

(defn count-words [path]
  ;; Prepare map of { word1 count1, word2 count2, .......}
  (->> (read-file path)
       (map #(clean-line %))
       (map #(frequencies %))
       (reduce #(merge-with + %1 %2) {})))

;; Sort map by values in descending order.
(defn sort-word-count [imap]
  (into (sorted-map-by (fn [key1 key2]
                   (compare [(get imap key2) key2]
                            [(get imap key1) key1]))) imap))

(defn -main
  [filepath]
  (println (time (sort-word-count (count-words (first filepath))))))

;; $ clj -M src/clj_example/word-counter.clj "./kjvbible.txt"
;; Uncomment line below to run from command line.
(-main *command-line-args*)
