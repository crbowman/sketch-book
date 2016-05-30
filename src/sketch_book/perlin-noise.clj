(ns sketch-book.perlin-noise
  (:require [quil.core :refer :all]
            [quil.helpers.drawing :refer [line-join-points]]
            [quil.helpers.seqs :refer [range-incl perlin-noise-seq]]
            [quil.helpers.calc :refer [mul-add]]
            [quil.middleware :as m]))

(defn setup []
  (smooth)
  (no-stroke)
  (color-mode :hsb)
  (fill 200)
  (frame-rate 30)
  (background 240)
  {:tx 0
   :ty 100})

(defn update-state [state] 
  {:tx (+ (:tx state) 0.01)
   :ty (+ (:ty state) 0.01)})
   
(defn draw-sketch [state]
  (let [py (noise (:ty state)) 
        px (noise (:tx state)) 
        x (* px 500)
        y (* py 500)]
    (ellipse x y 10 10)))
  
(defsketch perlin-walk-1
  :title "Perlin noise random walk"
  :setup setup
  :update update-state
  :draw draw-sketch
  :size [500 500]
  :middleware [m/fun-mode])
