(ns sketch-book.dots
  (:use quil.core
        sketch-book.util))

(def palette [(hex-to-rgb "##1E172D")
              (hex-to-rgb "#F83163")
              (hex-to-rgb "#01EADE")])

(defn setup-dots []
  (color-mode :rgb)
  ;; (apply background (first palette))
  (no-stroke)
  (smooth))

(def WIDTH 500)
(def HEIGHT 500)
(def dot-per-row 10)
(def increment (/ WIDTH dot-per-row))
(def dots (map (fn [y odd]
                 (let [start (if odd (- (/ increment 2)) increment)]
                   (for [x (range (int start) WIDTH increment)]
                      [x y (if odd (second palette) (nth palette 2)) odd])))
               (range (- (/  increment 2)) (* HEIGHT 1.3) (* 0.9 increment))
               (cycle [true false])))

(defn draw-dots []
  (frame-rate 1)
  (apply background (nth palette 2))
  (println  "Begin draw-dots")
  (doseq [row (drop-last 3 dots)]
    (println "  Outer loop")
    (doseq [[x y c odd] row]
      (println "    Inner loop")
      (apply fill (concat c [200]))
      (let [max-r 35
            odd-r (* max-r (abs (sin (radians (* 18 (mod (frame-count) 20))))))
            even-r (- max-r odd-r)]
        (if odd
          (ellipse x y odd-r odd-r)
          (ellipse x y even-r even-r))))))

(defsketch dots
  :title "dots"
  :setup setup-dots
  :draw draw-dots
  :renderer :p2d
  :size [WIDTH HEIGHT])
