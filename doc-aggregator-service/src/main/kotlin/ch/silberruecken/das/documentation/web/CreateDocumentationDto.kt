package ch.silberruecken.das.documentation.web

import ch.silberruecken.das.documentation.Documentation
import ch.silberruecken.das.documentation.DocumentationAccess
import ch.silberruecken.das.documentation.DocumentationType
import ch.silberruecken.das.documentation.Version
import org.jetbrains.annotations.NotNull
import java.net.URI

/**
 * @param uri Must be a complete uri, as the service cannot guest the origin's hostname.
 */
data class CreateDocumentationDto(
    @field:NotNull val type: DocumentationType?,
    @field:NotNull val service: String?,
    @field:NotNull val uri: URI?,
    private val version: Version?
) {
    fun toDomain(): Documentation {
        return Documentation(null, type!!, service!!, uri!!, DocumentationAccess.PUBLIC, version)
    }
}
