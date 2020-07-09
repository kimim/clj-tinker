(ns km.big-data
  (:require [dk.ative.docjure.spreadsheet :as xls]))
(->> (xls/load-workbook "resources/namelist.xlsx")
     (xls/select-sheet #".*1")
     (xls/select-columns {:A :name :B :school :C :age :D :birthday}))

(->> (xls/load-workbook "resources/namelist.xlsx")
     ((fn [book] (map #(xls/select-sheet % book) [#".*1" #"Sheet2"])))
     (map #(xls/select-columns {:A :name :B :school :C :age :D :birthday} %))
     (println))
