package ch.silberruecken.das.documentation.web

import ch.silberruecken.das.documentation.Documentation
import ch.silberruecken.das.documentation.DocumentationAccess
import ch.silberruecken.das.documentation.DocumentationType
import org.jetbrains.annotations.NotNull
import org.springframework.http.HttpHeaders
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

data class CreateDocumentationDto(
    @field:NotNull val type: DocumentationType?,
    @field:NotNull val service: String?,
    @field:NotNull val uri: URI?
) {
    fun toDomain(request: ServerHttpRequest): Documentation {
        val host = request.headers[HttpHeaders.HOST]?.first() ?: throw IllegalStateException("Could not find required header HOST")
        val uriWithHost = UriComponentsBuilder.fromUri(uri)
            .scheme(getScheme(host))
            .host(host)
            .build()
            .toUri()
        return Documentation(null, type!!, service!!, uriWithHost, DocumentationAccess.PUBLIC)
    }

    /**
     * We always assume https unless for localhost.
     * // TODO: This should be overrideable in the auto-configuration.
     */
    private fun getScheme(host: String) = if (host == "localhost") "http" else "https"
}