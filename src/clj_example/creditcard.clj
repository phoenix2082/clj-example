(ns clj-example.creditcard
  (:gen-class))

;;;; Modelling Credit Card using Records

;;; Protocol   - The function each card must support.
;;; CreditCard - Using record to model CreditCard.

(defprotocol Card
  (charge [this price])
  (make-payment [this amount])
  (print-summary [this]))

(defrecord CreditCard [customer bank account limit balance]
  ;; Implent Card Protocol
  Card
  (charge
    ^{:doc "Charge credit card.
            Charging will be only allowed if not above the card limit."}
    [this price]
    (if (> (+ price (:balance this)) (:limit this))
      false
      (assoc this :balance (+ (:balance this) price))))

  (make-payment
    ^{:doc "Make payment against card. Reduced balance."}
    [this amount]
    (assoc this :balance (- (:balance this) amount)))

  (print-summary
    ^{:doc "Print Credit Card Summary."}
    [this]
    (println "Customer = " (:customer this))
    (println "Bank     = " (:bank this))
    (println "Account  = " (:account this))
    (println "Balance  = " (:balance this))
    (println "Limit    = " (:limit this))))


  
    
