(ns morph.flume
  (:gen-class)
  (:import (org.apache.flume Event EventDeliveryException)
           (org.apache.flume.api RpcClient RpcClientFactory)
           (org.apache.flume.event EventBuilder)
           (java.nio.charset Charset)))

(defn simple-client [host port message]
  (with-open [client (RpcClientFactory/getDefaultInstance host (java.lang.Integer. port))]
    (let [event (EventBuilder/withBody (str message) (Charset/forName "UTF-8"))]
      (try
        (.append client event)
        (catch EventDeliveryException e
          (println "EPIC FAIL:" e))))))
