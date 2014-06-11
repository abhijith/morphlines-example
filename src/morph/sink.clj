(ns morph.core
  (:gen-class)
  (:import (java.io File BufferedInputStream FileInputStream))
  (:import (com.cloudera.cdk.morphline.api MorphlineContext MorphlineContext$Builder Record Command))
  (:import (com.cloudera.cdk.morphline.base Notifications Fields))
  (:import (com.cloudera.cdk.morphline.base.Compiler)))
