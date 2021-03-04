(ns clj-example.scoreboard
  (:gen-class))

;; Record to store name and score of a player
(defrecord GameEntry [name score])

;; Scoreboard. Set to store top 10 player
(def scoreboard #{})

(defn keep-10 [scoreboard]
  ;; Keeps only top 10 score.
  (if (> (count scoreboard) 10)
    (butlast scoreboard)
    scoreboard))

(defn add-entry [scoreboard entry]
  ;; Add Game Entry to score board
  ;; Returns scores in descending order.
  (->>  (conj scoreboard entry)
        (sort-by :score >)
        (keep-10)))

(defn remove-entry [scoreboard pname]
  ;; Remove a player from scoreboard by name
  ;; Returns scoreboard after remove gameentry
  (remove #(= pname (:name %)) scoreboard))
