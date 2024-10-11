package ch.silberruecken.das.documentation.web

import ch.silberruecken.das.documentation.Documentation
import ch.silberruecken.das.documentation.DocumentationAccess
import ch.silberruecken.das.documentation.DocumentationType
import org.jetbrains.annotations.NotNull
import java.net.URI

data class CreateDocumentationDto(
    @field:NotNull val type: DocumentationType?,
    @field:NotNull val service: String?,
    @field:NotNull val uri: URI?
) {
    fun toDomain() = Documentation(null, type!!, service!!, uri!!, DocumentationAccess.PUBLIC)
}