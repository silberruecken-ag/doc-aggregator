package ch.silberruecken.daa.client

import ch.silberruecken.daa.Documentation
import ch.silberruecken.daa.DocumentationType
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.web.server.autoconfigure.ServerProperties
import java.net.URI

@ConfigurationProperties(prefix = "doc-aggregator")
data class DocAggregatorProperties(
    val serverProperties: ServerProperties?, val enabled: Boolean = true,
    val docAggregatorUrl: URI = URI("http://localhost:8080"),
    val baseUrl: URI = URI("http://localhost:${serverProperties?.port ?: 8081}"),
    private val documentations: List<Documentation> = mutableListOf(
        Documentation(DocumentationType.API, URI("/docs/index.html"), null)
    )
) {

    fun getDocumentationWithFullUrls() = documentations.map { documentation ->
        documentation.copy(url = baseUrl.resolve(documentation.url))
    }
}
