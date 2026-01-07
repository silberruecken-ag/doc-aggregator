package ch.silberruecken.daa

import ch.silberruecken.daa.client.DocAggregatorClient
import ch.silberruecken.daa.client.DocAggregatorProperties
import ch.silberruecken.daa.client.DocAggregatorRestClient
import ch.silberruecken.daa.updater.DocumentationUpdater
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.core.env.Environment
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.client.OAuth2ClientHttpRequestInterceptor
import org.springframework.web.client.RestClient

@AutoConfiguration
@EnableConfigurationProperties(DocAggregatorProperties::class)
@ConditionalOnProperty(prefix = "doc-aggregator", name = ["enabled"], havingValue = "true", matchIfMissing = true)
@ConditionalOnClass(RestClient::class, OAuth2AuthorizedClientManager::class)
class DocAggregatorAutoConfiguration {
    @ConditionalOnBean(AuthorizedClientServiceOAuth2AuthorizedClientManager::class)
    @ConditionalOnMissingBean
    @Bean
    fun docAggregatorClient(
        restClientBuilder: RestClient.Builder,
        docAggregatorProperties: DocAggregatorProperties,
        environment: Environment,
        authorizedClientManager: AuthorizedClientServiceOAuth2AuthorizedClientManager
    ): DocAggregatorClient {
        val requestInterceptor = OAuth2ClientHttpRequestInterceptor(authorizedClientManager)
        val restClient = restClientBuilder.baseUrl(docAggregatorProperties.docAggregatorUrl)
            .requestInterceptor(requestInterceptor)
            .build()
        return DocAggregatorRestClient(
            restClient,
            environment.getProperty("spring.application.name") ?: throw IllegalArgumentException("doc-aggregator requires the spring.application.name property to be set")
        )
    }

    @ConditionalOnBean(ClientRegistrationRepository::class, OAuth2AuthorizedClientService::class)
    @ConditionalOnMissingBean
    @Bean
    fun authorizedClientServiceOAuth2AuthorizedClientManager(clientRegistrationRepository: ClientRegistrationRepository, authorizedClientService: OAuth2AuthorizedClientService) =
        AuthorizedClientServiceOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientService)

    @ConditionalOnMissingBean
    @Bean
    fun documentationUpdater(
        docAggregatorClient: DocAggregatorClient,
        docAggregatorProperties: DocAggregatorProperties
    ) = DocumentationUpdater(docAggregatorClient, docAggregatorProperties)
}
