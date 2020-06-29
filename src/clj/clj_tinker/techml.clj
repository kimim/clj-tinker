(ns clj-tinker.techml
  (:require [tech.verify.ml.classification :as classify-verify]
            [tech.libs.xgboost]
            [tech.libs.smile]
            [tech.ml :as ml]
            [tech.ml.loss :as loss]
            [tech.ml.dataset :as ds]
            [tech.ml.dataset.pipeline :as dsp]
            [tech.v2.datatype :as dtype]
            [tech.ml.dataset.pipeline.pipeline-operators
             :refer [without-recording
                     pipeline-train-context
                     pipeline-inference-context]]
            [tech.ml.dataset.pipeline.column-filters :as cf]
            ))

(first (classify-verify/mapseq-dataset))

(def fruits (ds/->dataset (classify-verify/mapseq-dataset)))

(dtype/shape fruits)

(println (ds/select fruits :all (range 10)))

(defn fruit-pipeline
  [dataset]
  (-> dataset
      (ds/remove-columns [:fruit-subtype :fruit-label])
      (dsp/string->number)
      (dsp/range-scale #(cf/not cf/categorical?))
      (ds/set-inference-target :fruit-name)))

(def processed-ds (fruit-pipeline fruits))

(println (ds/select processed-ds :all (range 10)))

(def model (ml/train {:model-type :smile.regression/elastic-net}
                     processed-ds))
