package ch.silberruecken.daa.client

import ch.silberruecken.daa.Documentation
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.client.RestClient
import java.net.URI

class DocAggregatorRestClient(private val restClient: RestClient, private val applicationName: String) : DocAggregatorClient {
    override fun updateDocumentation(documentation: Documentation) {
        restClient.post().uri("/documentations") // TODO: Share uri and body in shared module
            .body(CreateDocumentationDto.fromDomain(documentation, applicationName))
            .contentType(APPLICATION_JSON)
            .retrieve()
            .toBodilessEntity()
    }
}

data class CreateDocumentationDto(val type: DocumentationType, val service: String, val uri: URI) {
    companion object {
        fun fromDomain(documentation: Documentation, applicationName: String) = documentation.run {
            CreateDocumentationDto(DocumentationType.valueOf(type.toString()), applicationName, url)
        }
    }
}

enum class DocumentationType { API }
