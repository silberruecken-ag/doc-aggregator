[![Gradle Build](https://github.com/silberruecken-ag/doc-aggregator/actions/workflows/gradle-build.yml/badge.svg)](https://github.com/silberruecken-ag/doc-aggregator/actions/workflows/gradle-build.yml)

# Local test setup

* Start docker environment
* Run [DemoAuthServerApplication](demo-auth-server/src/main/kotlin/demo/auth/DemoAuthServerApplication.kt)
* Run [TestDocAggregatorServiceApplication](doc-aggregator-service/src/test/kotlin/ch/silberruecken/das/TestDocAggregatorServiceApplication.kt) (with `testdata` profile if needed)
* Run [DemoPublicApplication](demo-public/src/main/kotlin/demo/public/DemoPublicApplication.kt) to register public demo documentation

[//]: # (* TODO: Run ??? to register protected demo documentation)