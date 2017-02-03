(ns soundz.core
  (:use overtone.core)
  (:require [overtone.synth.retro :as r]))
(connect-external-server 57110)

;generate a single tb-303 tone
(defn tb-note []
  (let [note 45
        wave 1
        cutoff 240
        env  1000
        res 0.8
        sus 0.7
        dec 0.2
        amp 0.5
        gate 1
        action FREE]
    (r/tb-303 note wave cutoff 
              env res sus dec 
              amp gate action)))

; repeatedly call tb-303 every beat
(defn loop-note [met sound]
  (let [beat (met)]
    (at (met beat)
      (sound))
    (apply-by (met (inc beat)) loop-note met sound [])))

; make a metronome for loop-note to follow
(def m (metronome 90))

; Run to create a continuous note-maker
(loop-note m tb-note)

; Stop the notes by stopping the metronome
(stop)

(kill-server)
