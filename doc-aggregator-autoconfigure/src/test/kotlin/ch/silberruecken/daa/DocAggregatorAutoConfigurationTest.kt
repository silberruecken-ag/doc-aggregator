package ch.silberruecken.daa

import ch.silberruecken.daa.DocAggregatorAutoConfigurationTest.UserConfiguration.Companion.mockRequest
import ch.silberruecken.daa.DocAggregatorAutoConfigurationTest.UserConfiguration.Companion.mockServer
import ch.silberruecken.daa.client.DocAggregatorClient
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.mock
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.AutoConfigurations
import org.springframework.boot.autoconfigure.logging.ConditionEvaluationReportLoggingListener
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.logging.LogLevel
import org.springframework.boot.test.context.assertj.AssertableApplicationContext
import org.springframework.boot.test.context.runner.ApplicationContextRunner
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess
import org.springframework.web.client.RestClient
import java.time.Duration

class DocAggregatorAutoConfigurationTest {

    @Test
    fun `should send documentation location to documents aggregator service`() {
        val contextRunner = withContextRunner()

        contextRunner.withUserConfiguration(UserConfiguration::class.java)
            .withPropertyValues(
                "spring.application.name=test-service",
                "doc-aggregator.base-url=https://test-service.io"
            )
            .run { context ->
                mockRequest("http://localhost:8080", "https://test-service.io/docs/index.html")

                publishEvent(context)

                assertThat(context).hasSingleBean(DocAggregatorClient::class.java)
                mockServer.verify()
            }
    }

    @Test
    fun `should send external documentation`() {
        val contextRunner = withContextRunner()

        contextRunner.withUserConfiguration(UserConfiguration::class.java)
            .withPropertyValues(
                "spring.application.name=test-service",
                "doc-aggregator.documentations[0].url=https://my-documentation.io",
                "doc-aggregator.documentations[0].version=1.1-SNAPSHOT",
                "doc-aggregator.doc-aggregator-url=https://doc-aggregator.io"
            )
            .run { context ->
                mockRequest("https://doc-aggregator.io", "https://my-documentation.io", "1.1-SNAPSHOT")

                publishEvent(context)

                assertThat(context).hasSingleBean(DocAggregatorClient::class.java)
                mockServer.verify()
            }
    }

    private fun publishEvent(context: AssertableApplicationContext) {
        val applicationReadyEvent = ApplicationReadyEvent(
            SpringApplication(DocAggregatorAutoConfigurationTest::class.java),
            emptyArray(),
            context as ConfigurableApplicationContext,
            Duration.ofMillis(500L)
        )
        context.publishEvent(applicationReadyEvent)
    }

    private fun withContextRunner(): ApplicationContextRunner = ApplicationContextRunner()
        .withConfiguration(AutoConfigurations.of(DocAggregatorAutoConfiguration::class.java))
        .withInitializer(ConditionEvaluationReportLoggingListener.forLogLevel(LogLevel.INFO))

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

        @Bean
        fun authorizedClientServiceOAuth2AuthorizedClientManager() = mock<AuthorizedClientServiceOAuth2AuthorizedClientManager> {
        }
    }
}
