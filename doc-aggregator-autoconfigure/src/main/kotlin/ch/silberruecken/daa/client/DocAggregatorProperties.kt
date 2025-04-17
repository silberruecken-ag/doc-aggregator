package ch.silberruecken.daa.client

import ch.silberruecken.daa.Documentation
import ch.silberruecken.daa.DocumentationType
import org.springframework.boot.context.properties.ConfigurationProperties
import java.net.URI

@ConfigurationProperties(prefix = "doc-aggregator")
class DocAggregatorProperties {
    var enabled = true
    var serviceUrl: URI = URI("http://localhost:8080")
    val documentations = mutableListOf(
        Documentation(DocumentationType.API, URI("/docs/index.html"), null)
    )
}
