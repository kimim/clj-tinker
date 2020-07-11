(defproject kimim/clj-tinker "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://github.com/kimim/clj-tinker"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :min-lein-version "2.9.1"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/clojurescript "1.10.520"]
                 [org.clojure/core.async  "0.4.500"]
                 [org.clojure/java.jdbc "0.7.11"]
                 [clojure.java-time "0.3.2"]
                 [compojure "1.6.0"]
                 [ring/ring-core "1.8.0"]
                 [cider/piggieback "0.4.2"]
                 [garden "1.3.3"]
                 [cljs-http "0.1.46"]
                 [clj-time "0.14.0"]
                 [reagent "1.0.0-alpha2"]
                 [re-frame "0.11.0"]
                 [cljsjs/material-ui "4.9.12-0"]
                 [cljsjs/material-table "1.57.2-0"]
                 [cljsjs/material-ui-icons "4.4.1-0"]
                 [metasoarous/oz "1.6.0-alpha6"]
                 [metasoarous/darkstar "0.1.0"]
                 [dk.ative/docjure "1.14.0"]
                 [techascent/tech.ml "2.01"]
                 [techascent/tech.ml.dataset "2.07"]
                 [techascent/tech.viz "0.3"]
                 [scicloj/tablecloth "1.0.0-pre-alpha4"]
                 [net.mikera/core.matrix "0.62.0"]
                 [uncomplicate/neanderthal "0.32.0"]
                 [org.clojars.haifengl/smile "2.4.0"]]
  :plugins [[lein-figwheel "0.5.19"]
            [lein-cljsbuild "1.1.7" :exclusions [[org.clojure/clojure]]]
            [lein-garden "0.3.0"]
            [kimim/lein-ring "1.0.2"]
            [cider/cider-nrepl "0.25.2"]]
  :main ^:skip-aot clj-tinker.core
  :target-path "target/%s"
  :source-paths ["src" "src/clj"]
  :cljsbuild {:builds
              [{:id "dev"
                :source-paths ["src/cljs"]
                :figwheel {:on-jsload "km.core/on-js-reload"
                           :websocket-host "localhost"}
                :compiler {:main km.core
                           :asset-path "js" ;; very important, this is the relative path of asset!
                           :output-to "resources/public/js/km.js"
                           :output-dir "resources/public/js"
                           :source-map-timestamp true
                           ;; To console.log CLJS data-structures make sure you enable devtools in Chrome
                           ;; https://github.com/binaryage/cljs-devtools
                           :preloads [devtools.preload]}}
               {:id "min"
                :source-paths ["src/cljs"]
                :compiler {:output-to "resources/public/js/km.js"
                           :main km.core
                           :optimizations :advanced
                           :pretty-print false}}]}
  :garden {:builds [{:id "screen"
                     :source-paths ["src/garden"]
                     :stylesheet km.core/main
                     :compiler {:output-to "resources/public/css/main.css"
                                :pretty-print? false}}]}
  :figwheel {:css-dirs ["resources/public/css"]
             :nrepl-port 3999}
  :ring {:handler km.core/app
         :nrepl {:start? true}}
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[binaryage/devtools "0.9.10"]
                                  [figwheel-sidecar "0.5.19"]]
                   ;; need to add dev source path here to get user.clj loaded
                   :source-paths ["src" "dev"]
                   ;; need to add the compliled assets to the :clean-targets
                   :clean-targets ^{:protect false} ["resources/public/js"
                                                     :target-path]
                   :ring {:port 1819}}})
