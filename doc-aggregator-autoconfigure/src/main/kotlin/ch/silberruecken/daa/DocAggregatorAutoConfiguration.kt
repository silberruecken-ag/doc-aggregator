package ch.silberruecken.daa

import ch.silberruecken.daa.client.DocAggregatorClient
import ch.silberruecken.daa.client.DocAggregatorRestClient
import ch.silberruecken.daa.client.DocAggregatorProperties
import ch.silberruecken.daa.updater.DocumentationUpdater
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.core.env.Environment
import org.springframework.web.client.RestClient

@AutoConfiguration
@EnableConfigurationProperties(DocAggregatorProperties::class)
@ConditionalOnProperty(prefix = "doc-aggregator", name = ["enabled"], havingValue = "true", matchIfMissing = true)
@ConditionalOnClass(RestClient::class)
class DocAggregatorAutoConfiguration {
    @ConditionalOnMissingBean
    @Bean
    fun docAggregatorClient(
        restClientBuilder: RestClient.Builder,
        docAggregatorProperties: DocAggregatorProperties,
        environment: Environment
    ): DocAggregatorClient {
        val restClient = restClientBuilder.baseUrl(docAggregatorProperties.serviceUrl).build()
        return DocAggregatorRestClient(
            restClient,
            environment.getProperty("spring.application.name") ?: throw IllegalArgumentException("doc-aggregator requires the spring.application.name property to be set")
        )
    }

    @ConditionalOnMissingBean
    @Bean
    fun documentationUpdater(
        docAggregatorClient: DocAggregatorClient,
        docAggregatorProperties: DocAggregatorProperties
    ) = DocumentationUpdater(docAggregatorClient, docAggregatorProperties)
}
