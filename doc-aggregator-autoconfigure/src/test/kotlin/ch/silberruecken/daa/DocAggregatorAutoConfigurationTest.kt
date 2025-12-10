package ch.silberruecken.daa

import ch.silberruecken.daa.client.DocAggregatorClient
import ch.silberruecken.daa.updater.DocumentationUpdater
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.getBean
import org.springframework.boot.autoconfigure.AutoConfigurations
import org.springframework.boot.autoconfigure.logging.ConditionEvaluationReportLoggingListener
import org.springframework.boot.logging.LogLevel
import org.springframework.boot.test.context.runner.ApplicationContextRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess
import org.springframework.web.client.RestClient

class DocAggregatorAutoConfigurationTest {

    @Test
    fun `should send documentation location to documents aggregator service`() {
        val contextRunner = ApplicationContextRunner().withConfiguration(AutoConfigurations.of(DocAggregatorAutoConfiguration::class.java))
            .withInitializer(ConditionEvaluationReportLoggingListener.forLogLevel(LogLevel.INFO))

        contextRunner.withUserConfiguration(UserConfiguration::class.java)
            .withPropertyValues("spring.application.name=test-service")
            .run { context ->

                // Don't send ApplicationReadyEvent, simply trigger function directly.
                context.getBean<DocumentationUpdater>().onApplicationReady()

                assertThat(context).hasSingleBean(DocAggregatorClient::class.java)
                context.getBean<UserConfiguration>().mockServer.verify() // TODO: This fails because the application ready event was probably not fired
            }
    }

    @Configuration(proxyBeanMethods = false)
    class UserConfiguration {
        lateinit var mockServer: MockRestServiceServer

        @Bean
        fun restClientBuilder(): RestClient.Builder {
            val restClientBuilder = RestClient.builder()

            mockServer = MockRestServiceServer.bindTo(restClientBuilder).build()
            mockServer.expect(requestTo("http://localhost:8080/documentations"))
                .andExpect(jsonPath("type").value("API"))
                .andExpect(jsonPath("service").value("test-service"))
                .andExpect(jsonPath("uri").value("/docs/index.html"))
                .andRespond(withSuccess())
            return restClientBuilder
        }
    }
}
