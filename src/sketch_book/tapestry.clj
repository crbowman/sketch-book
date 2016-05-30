(ns sketch.tapestry
    (:require [quil.core :refer :all]
              [quil.middleware :as m]))


(defrecord Vector [x y z])

(def wander-speed 0.7)
(def line-hue 1.0)
(def line-sat 0.0)
(def line-bright 1.0)
(def frame-width 940)
(def frame-height 540)
(def num-steps 10)
(def color-trigger 0.90)

(defn create-line []
      (for [i (range num-steps)
            :let [x (/ (* i frame-width) num-steps)
                  y (/ frame-height 2)]]
        (Vector. x y 0)))

(def vectors (create-line))

(defn randomize-color []
  (def line-hue (rand 1))
  (def line-sat (rand 1)))

(defn setup []
 (color-mode :hsb 1.0 1.0 1.0 1.0)
 (background 0.0)
 (no-fill)
 (randomize-color)
 (frame-rate 30))

(defn draw-frame []
    (stroke-weight 1.01)
    (if (> (rand 1) color-trigger)
        (randomize-color))
    (stroke line-hue line-sat line-bright 0.05)
    (fill 255)
    (begin-shape)
    (for [i (range (count vectors))
          :let [v (nth vectors i)]]
        (do (if (== i 0) (curve-vertex (:x v) (:y v)))
            (curve-vertex (:x v) (:y v))
            (if (== i (- (count vectors) 1)) (curve-vertex (:x v) (:y v)))))
    (end-shape))


(defsketch tapestry
  :title "Perlin noise random fill"
  :setup setup
  :draw draw-frame
  :size [frame-width frame-height])
