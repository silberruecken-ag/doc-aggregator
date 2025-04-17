package ch.silberruecken.daa.client

import ch.silberruecken.daa.Documentation
import org.springframework.web.client.RestClient

class DocAggregatorRestClient(
    private val restClient: RestClient,
    private val docAggregatorProperties: DocAggregatorProperties
) : DocAggregatorClient {
    override fun updateDocumentation(documentation: Documentation) {
        TODO("Not yet implemented")
    }
}
