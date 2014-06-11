(ns morph.core
  (:gen-class)
  (:import (java.io File BufferedInputStream FileInputStream))
  (:import (com.cloudera.cdk.morphline.api MorphlineContext MorphlineContext$Builder Record Command))
  (:import (com.cloudera.cdk.morphline.base Notifications Fields))
  (:import (com.cloudera.cdk.morphline.base.Compiler)))

;;; java source:
;;; cdk/cdk-morphlines/cdk-morphlines-core/src/test/java/com/cloudera/cdk/morphline/api/MorphlineDemo.java
(def notifications {:transaction-begin    Notifications/notifyBeginTransaction
                    :transaction-commit   Notifications/notifyCommitTransaction
                    :transaction-rollback Notifications/notifyRollbackTransaction
                    :session-start        Notifications/notifyStartSession
                    :shutdown             Notifications/notifyShutdown
                    })

(defn -main [& args]
  (if-not (= (count args) 2)
    (println "USAGE: java -jar morph/target/morph-x.x.x-SNAPSHOT-standalone.jar <morphline>.conf <input-file>")
    (let [morphline-context (.build (MorphlineContext$Builder.))
          morphline-id      ""
          morphline-file    (File. (first args))
          compiler          (new com.cloudera.cdk.morphline.base.Compiler)
          morphline         (.compile compiler morphline-file morphline-id morphline-context nil (make-array com.typesafe.config.Config 0))]
      ((notifications :transaction-begin) morphline)
      (try
        (with-open [in (BufferedInputStream. (FileInputStream. (File. (second args))))]
          (let [record (Record.)]
            (.put record Fields/ATTACHMENT_BODY in)
            ((notifications :session-start) morphline)
            (when-not (.process morphline record)
              (println (format "Morphline failed to process record: %s" record)))))
        ((notifications :transaction-commit) morphline)
        (catch RuntimeException e
          ((notifications :transaction-rollback) morphline)
          (.handleException (.getExceptionHandler morphline-context) e nil)))
      ((notifications :shutdown) morphline))))
