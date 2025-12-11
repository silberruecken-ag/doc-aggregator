package ch.silberruecken.das.documentation.web

import ch.silberruecken.das.documentation.Documentation
import ch.silberruecken.das.documentation.DocumentationService
import ch.silberruecken.das.documentation.web.DocumentationRestController.Companion.REQUEST_MAPPING
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping(REQUEST_MAPPING)
class DocumentationRestController(private val documentationService: DocumentationService) {
    @PutMapping
    fun createOrUpdateDocumentation(@Valid @RequestBody documentation: CreateDocumentationDto, request: HttpServletRequest): ResponseEntity<Documentation> {
        return documentationService.createOrUpdateDocumentation(documentation.toDomain(request))
            .let { ResponseEntity.created(URI("$REQUEST_MAPPING/${it.id}")).body(it) }
    }

    companion object {
        const val REQUEST_MAPPING = "/documentations"
    }
}