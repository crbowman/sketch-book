(ns sketch-book.de-jong
  (:use quil.core
        sketch-book.util))

(def palette [(hex-to-rgb "#e0e0e0")
              (hex-to-rgb "#B6B081")
              (hex-to-rgb "#CAA13C")
              (hex-to-rgb "#AB5008")
              (hex-to-rgb "#2E524E")])

(def palette [(hex-to-rgb "#1E172D")
              (hex-to-rgb "#F83163")
              (hex-to-rgb "#01EADE")
              (hex-to-rgb "#FBC02D")])

(def WIDTH 800)
(def HEIGHT 800)



(def params {:a 1.4
             :b -2.3
             :c 2.4
             :d -2.1})

(def params {:a 1.641
             :b 1.902
             :c 0.316
             :d 1.525})

(def params {:a -2.7
             :b -0.09
             :c -0.86
             :d -2.2})


(def px (atom 0.0))
(def py (atom 0.0))

(defn de-jong-ifs [[x y] params]
   (let [a (:a params)
         b (:b params)
         c (:c params)
         d (:d params)]
    [(- (sin (* a y))
        (cos (* b x)))
     (- (sin (* c x))
        (cos (* d y)))]))

(defn remap [value start1 stop1 start2 stop2]
    (let [delta2 (- stop2 start2)
          delta1 (- stop1 start1)
          deltav (- value start1)
          step-size (/ delta1 delta2)]
        (floor (/ deltav step-size))))

(def img
    (apply vector
        (map (fn [_]
               (apply vector (map (fn [_] {:count 0})
                                  (range HEIGHT))))
             (range WIDTH))))
 
(defn pixel [x y]
    (-> img (nth x) (nth y)))

(defn inc-pixel [x y]
  (let [p (pixel x y)
        c (+ (:count p) 1)]
    (assoc p :count c)
    c))

(defn setup []
    (background 0)
    (smooth))

(def params-1 {:a 1.641
               :b 1.902
               :c 0.316
               :d 1.525})

(def params-2 {:a 2.01
               :b -2.53
               :c 1.61
               :d -0.33})

(def params-3 {:a -0.827
               :b -1.637
               :c 1.659
               :d -0.943})

(def params-4 {:a -0.709
               :b 1.638
               :c -0.452
               :d 1.740})

(defn draw []
  (smooth)
  (frame-rate 400)
  ; (apply stroke (nth palette 2))
  (let [[dx dy] (de-jong-ifs [@px @py] params-1)
        x (remap dx -2 2 0 WIDTH)
        y (remap dy -2 2 0 HEIGHT)]
      (println (inc-pixel x y))
      (stroke (* 20 (:count (pixel x y))))
      (point x y)
      (reset! px dx)
      (reset! py dy)))


(defn savef []
    (save (str "/Users/curtis/code/sketch/output/de-jong-" (frame-count) ".png")))



(defsketch de-jong
    :title "De Jong Iterative Function System"
    :setup setup
    :draw draw
    :size [WIDTH HEIGHT]
    :features [:keep-on-top])
