(ns sketch-book.rotation 
  (:use quil.core
        sketch-book.util
        quil.middleware))


(def WIDTH 800)
(def HEIGHT 800)

(defn init-n [w h]
  (* 0.05 (* w h)))

(def THETA (radians 4))
(def NUM (init-n WIDTH HEIGHT))


(def palette {:eggplant  (hex-to-rgb "#1E172D")
              :pink (hex-to-rgb "#F83163")
              :turquoise (hex-to-rgb "#01EADE")})


(defn setup-sketch []
  (smooth)
  {:width WIDTH
   :height HEIGHT
   :pivot [0 20]
   :theta THETA
   :points []
   :n NUM
   :dot-size 2
   :scale 0.96})

(defn rand-cord [state]
  (let [w (* 1.1 (:width state))
        h (* 1.1 (:height state))]
  {:x (- (rand-int w) (/ w 2))
   :y (- (rand-int h) (/ h 2))}))


(defn update-state [state]
  (let [points (take (:n state) 
                 (repeatedly 
                   #(rand-cord state)))]
    {:width WIDTH
     :height HEIGHT
     :pivot [0 20]
     :theta THETA
     :points points
     :n NUM
     :dot-size 1
     :scale 0.97}))

(defn rotate-and-translate [state pnt]
  (let [a (:theta state)
        [dx dy] (:pivot state)
        x (:x pnt)
        y (:y pnt)]
    [(ceil (+ dx (- (* x (cos a)) (* y (sin a)))))
     (ceil (+ dy (* x (sin a)) (* y (cos a))))]))

      

(defn shrink [state pnt]
  (let [s (:scale state)
        x (:x pnt)
        y (:y pnt)]
   [(ceil (* s x)) (ceil (* s y))]))


(defn draw-frame [state]
    (apply stroke (palette :turquoise))
    (apply background (:eggplant palette))
    (translate (/ (:height state) 2) (/ (:width state) 2))
    (let [ps (:points state)
          d (:dot-size state)]
      (doseq [p ps]
        (let [[x y] (shrink state p)
              [dx dy] (rotate-and-translate state p)]
          (point x y)
          (point dx dy))))
    (save "/Users/curtis/code/sketch/output/rotation.tif")
  (save "/Users/curtis/code/sketch/output/rotation.png"))


(defsketch rotation
  :title "Randomness and rotaion"
  :setup setup-sketch
  :update update-state
  :draw draw-frame
  :features [:keep-on-top]
  :renderer :opengl
  :size [WIDTH HEIGHT]
  :middleware [fun-mode])
