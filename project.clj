(defproject morph "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :repositories [["cdh.repo" "https://repository.cloudera.com/artifactory/cloudera-repos"]]
  :dependencies [[org.clojure/clojure    "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [org.apache.flume/flume-ng-sdk "1.3.0-cdh4.2.0"]
                 [io.netty/netty "3.6.6.Final"]
                 [org.apache.avro/avro   "1.7.5"]
                 [org.apache.avro/avro-ipc   "1.7.5"]
                 [org.slf4j/slf4j-api    "1.6.1"]
                 [org.slf4j/slf4j-log4j12 "1.6.1"]
                 [com.cloudera.cdk/cdk-morphlines-core "0.6.0"]]
  :aot [morph.core morph.flume]
  :main morph.core)
