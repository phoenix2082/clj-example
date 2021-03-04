(ns clj-example.ceaser
  (:gen-class))


;; Declare method which must be implemented
;; for encryption/decryption.

(defprotocol Cipher
  ;; Encoding used for message encryption
  (encoder [this])

  ;; Decoding used for message decryption
  (decoder [this])

  ;; Function for encryption
  (encrypt [this msg])

  ;; Function for decryption
  (decrypt [this msg])

  ;; Common method called by encrypt and decrypt funtion
  (-transform [this message coder]))

;; Record for doing encryption and decryption using Ceaser Cipher.
(defrecord CeaserCipher [rotation]
  Cipher
  (encoder [this]
    ;; Encoder used for encryption
    (char-array (for [k (range 26)]
                  (char (+ 65 (mod (+ k rotation) 26))))))
  
  (decoder [this]
    ;; Decoder ued for decryption
    (char-array (for [k (range 26)]
                (char (+ 65 (mod (+ 26 (- k rotation)) 26))))))

  (-transform [this message coder]
    ;; Common method used for encryption and decryption
    (let [msg (char-array message)]
      (apply str (map (fn [c]
                        (if (Character/isUpperCase c)
                          (aget coder (- (int c) 65))
                          c)) msg))))

  (encrypt [this message]
    ;; Encrypt the message.
    ;; Used encoder which is associated with this record instance.
    (-transform this message (encoder this)))

  (decrypt [this message]
    ;; Decrypt the message
    ;; Uses decoder which is associed with this record instance.
    (-transform this message (decoder this)))
)

