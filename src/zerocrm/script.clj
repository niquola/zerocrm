(ns  zerocrm.script
  "Scrypt encryption function.

  To learn more about scrypt, see https://www.tarsnap.com/scrypt/scrypt.pdf"
  (:import com.lambdaworks.crypto.SCryptUtil))


;;
;; API
;;

(defn gensalt  [&  [n]]
  (let  [charseq  (map char  (concat
                               (range 48 58) ; 0-9
                               (range 97 123)))] ; 0-z
    (apply str
           (take  (or n 10)
                 (repeatedly #(rand-nth charseq))))))

(defn ^String encrypt
  "Encrypts a string value using scrypt.

  Arguments are:

  s (string): a string to encrypt
  n (integer): CPU cost parameter (16384 is a good starting value)
  r (integer): RAM cost parameter (8 is a good starting value)
  p (integer): parallelism parameter (1 is a good starting value)"
  [^String s ^long n ^long r ^long p]
  (SCryptUtil/scrypt s n r p))

(defn verify
  "Verifies a value against a hash produced by scrypt"
  [^String candidate ^String hash]
  (SCryptUtil/check candidate hash))
