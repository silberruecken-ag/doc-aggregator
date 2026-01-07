[![Gradle Build](https://github.com/silberruecken-ag/doc-aggregator/actions/workflows/gradle-build.yml/badge.svg)](https://github.com/silberruecken-ag/doc-aggregator/actions/workflows/gradle-build.yml)

# Local test setup

* Start docker environment
* Run [DemoAuthServerApplication](demo-auth-server/src/main/kotlin/demo/auth/DemoAuthServerApplication.kt)
* Run [TestDocAggregatorServiceApplication](doc-aggregator-service/src/test/kotlin/ch/silberruecken/das/TestDocAggregatorServiceApplication.kt) (with `testdata` profile if needed)
* Run [DemoPublicApplication](demo-public/src/main/kotlin/demo/public/DemoPublicApplication.kt) to register public demo documentation

[//]: # (* TODO: Run ??? to register protected demo documentation)

# Running the Doc Aggregator Service

* Configure the following [Spring properties](https://docs.spring.io/spring-boot/reference/features/external-config.html):
    * `spring.security.oauth2.resourceserver.jwt.issuer-uri`: The isser uri of your authorization server (IAM).
    * `spring.security.oauth2.client.registration.auth.client-id` and `spring.security.oauth2.client.registration.auth.client-secret`: The client ID and client secret of your
      authorization server's configuration for the Doc Aggregator service.

## Connecting to the Doc Aggregator Service

Each application that wants to report its documentation(s), needs to be configured as follows:

* Add the `doc-aggregator-autoconfigure` dependency together with the Spring OAuth2 client starter.
* Configure an OAuth2 client registration `das`, e.g.
  ```yaml
  spring:
    security:
      oauth2:
        client:
          registration:
            das:
              client-id: demo-public
              client-secret: demo-public
              client-authentication-method: client_secret_basic
              scope:
                - das.documentations.write
              authorization-grant-type: client_credentials
              provider: auth-server
          provider:
            auth-server:
              issuer-uri: http://localhost:9000
  ```
  Configure the same issuer uri as you configured for the Doc Aggregator Service. The scope must be pre-configured in the authorization server.
* If needed, override the `doc-aggregator.*` properties defined
  in [DocAggregatorProperties](doc-aggregator-autoconfigure/src/main/kotlin/ch/silberruecken/daa/client/DocAggregatorProperties.kt).

If the application is not based on the Spring framework, it must fulfill the following requirements:

* The application must retrieve a JWT token with the `das.documentation.write` scope.
* During startup, it must send the same request as
  in [DocAggregatorRestClient](doc-aggregator-autoconfigure/src/main/kotlin/ch/silberruecken/daa/client/DocAggregatorRestClient.kt), including the JWT token.