(ns om-tutorial.state-reads.parser-3
  (:require [om.next :as om]))

(def app-state (atom {
                      :load/start-time 40000.0              ;stored in ms
                      }))

(defn convert [ms units]
  (case units
    :minutes (/ ms 60000.0)
    :seconds (/ ms 1000.0)
    :ms ms))

(defn read [{:keys [state]} key params]
  (case key
    :load/start-time {:value (convert (get @state key) (or (:units params) :ms))}
    nil))

(def parser (om/parser {:read read}))

(def parse-result-secs (parser {:state app-state} '[(:load/start-time {:units :seconds})]))
(def parse-result-mins (parser {:state app-state} '[(:load/start-time {:units :minutes})]))
(def parse-result-ms (parser {:state app-state} '[(:load/start-time {:units :ms})]))
