package ch.silberruecken.daa

import ch.silberruecken.daa.DocAggregatorAutoConfigurationTest.UserConfiguration.Companion.mockRequest
import ch.silberruecken.daa.DocAggregatorAutoConfigurationTest.UserConfiguration.Companion.mockServer
import ch.silberruecken.daa.client.DocAggregatorClient
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.AutoConfigurations
import org.springframework.boot.autoconfigure.logging.ConditionEvaluationReportLoggingListener
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.logging.LogLevel
import org.springframework.boot.test.context.runner.ApplicationContextRunner
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess
import org.springframework.web.client.RestClient
import java.time.Duration

class DocAggregatorAutoConfigurationTest {

    @Test
    fun `should send documentation location to documents aggregator service`() {
        val contextRunner = ApplicationContextRunner().withConfiguration(AutoConfigurations.of(DocAggregatorAutoConfiguration::class.java))
            .withInitializer(ConditionEvaluationReportLoggingListener.forLogLevel(LogLevel.INFO))

        contextRunner.withUserConfiguration(UserConfiguration::class.java)
            .withPropertyValues(
                "spring.application.name=test-service",
                "doc-aggregator.base-url=https://test-service.io"
            )
            .run { context ->
                mockRequest("http://localhost:8080", "https://test-service.io/docs/index.html")

                val applicationReadyEvent = ApplicationReadyEvent(
                    SpringApplication(DocAggregatorAutoConfigurationTest::class.java),
                    emptyArray(),
                    context as ConfigurableApplicationContext,
                    Duration.ofMillis(500L)
                )
                context.publishEvent(applicationReadyEvent)

                assertThat(context).hasSingleBean(DocAggregatorClient::class.java)
                mockServer.verify()
            }
    }

    @Test
    fun `should send external documentation`() {
        val contextRunner = ApplicationContextRunner().withConfiguration(AutoConfigurations.of(DocAggregatorAutoConfiguration::class.java))
            .withInitializer(ConditionEvaluationReportLoggingListener.forLogLevel(LogLevel.INFO))

        contextRunner.withUserConfiguration(UserConfiguration::class.java)
            .withPropertyValues(
                "spring.application.name=test-service",
                "doc-aggregator.documentations[0].url=https://my-documentation.io",
                "doc-aggregator.documentations[0].version=1.1-SNAPSHOT",
                "doc-aggregator.doc-aggregator-url=https://doc-aggregator.io"
            )
            .run { context ->
                mockRequest("https://doc-aggregator.io", "https://my-documentation.io", "1.1-SNAPSHOT")

                val applicationReadyEvent = ApplicationReadyEvent(
                    SpringApplication(DocAggregatorAutoConfigurationTest::class.java),
                    emptyArray(),
                    context as ConfigurableApplicationContext,
                    Duration.ofMillis(500L)
                )
                context.publishEvent(applicationReadyEvent)

                assertThat(context).hasSingleBean(DocAggregatorClient::class.java)
                mockServer.verify()
            }
    }

    @Configuration(proxyBeanMethods = false)
    class UserConfiguration {
        companion object {
            lateinit var mockServer: MockRestServiceServer

            fun mockRequest(docAggregatorUrl: String, documentationUrl: String, version: String? = null) {
                mockServer.expect(requestTo("$docAggregatorUrl/api/documentations"))
                    .andExpect(jsonPath("type").value("API"))
                    .andExpect(jsonPath("service").value("test-service"))
                    .andExpect(jsonPath("uri").value(documentationUrl))
                    .let {
                        if (version == null)
                            it.andExpect(jsonPath("version").doesNotExist())
                        else
                            it.andExpect(jsonPath("version").value(version))
                    }
                    .andRespond(withSuccess())
            }
        }

        @Bean
        fun restClientBuilder(): RestClient.Builder {
            val restClientBuilder = RestClient.builder()

            mockServer = MockRestServiceServer.bindTo(restClientBuilder).build()
            return restClientBuilder
        }
    }
}
