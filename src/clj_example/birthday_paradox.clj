(ns clj-example.birthdayparadox)


(defn birthday-probability [^long num]
  ;; Birthday Paradox - To find probablity of two people sharing same birthday.
  (- 1 (apply * (map #(/ (- 365 %) 365.0) (range num)))))

(defn check-bp []
  ;; Print probability for n = 5, 10, 15.... so on
  (for [n (range 5 100 5)]
    (println (str n ": " (birthday-probability n)))))
