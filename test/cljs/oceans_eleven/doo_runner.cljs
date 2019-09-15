(ns oceans-eleven.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [oceans-eleven.core-test]))

(doo-tests 'oceans-eleven.core-test)

