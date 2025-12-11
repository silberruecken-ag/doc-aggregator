package ch.silberruecken.das.documentation.web

import ch.silberruecken.das.documentation.Documentation
import ch.silberruecken.das.documentation.DocumentationAccess
import ch.silberruecken.das.documentation.DocumentationType
import ch.silberruecken.das.documentation.Version
import jakarta.servlet.http.HttpServletRequest
import org.jetbrains.annotations.NotNull
import org.springframework.http.HttpHeaders
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

data class CreateDocumentationDto(
    @field:NotNull val type: DocumentationType?,
    @field:NotNull val service: String?,
    @field:NotNull val uri: URI?,
    private val version: Version?
) {
    fun toDomain(request: HttpServletRequest): Documentation {
        val host = request.getHeader(HttpHeaders.HOST) ?: throw IllegalStateException("Could not find required header HOST")
        val uriWithHost = UriComponentsBuilder.fromUriString(getScheme(host) + "://" + host)
            .path(uri.toString())
            .build()
            .toUri()
        return Documentation(null, type!!, service!!, uriWithHost, DocumentationAccess.PUBLIC, version)
    }

    /**
     * We always assume https unless for localhost.
     * // TODO: This should be overrideable in the auto-configuration.
     */
    private fun getScheme(host: String) = if (host.startsWith("localhost")) "http" else "https"
}
