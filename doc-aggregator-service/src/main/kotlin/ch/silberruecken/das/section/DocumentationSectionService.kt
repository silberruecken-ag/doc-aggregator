package ch.silberruecken.das.section

import ch.silberruecken.das.documentation.Documentation
import ch.silberruecken.das.documentation.DocumentationService
import ch.silberruecken.das.section.elasticsearch.SectionIndexRepository
import org.springframework.stereotype.Service
import java.net.URI

// TODO: Security
@Service
class DocumentationSectionService(private val sectionIndexRepository: SectionIndexRepository, private val documentationService: DocumentationService) {
    fun findByQuery(query: String) = sectionIndexRepository.findByMarkupHtmlContainingOrderByMarkupElementDepthDesc(query)

    fun findDocsByQuery(query: String) = findByQuery(query)
        .map { it.content }
        .distinctBy { it.documentationId }
        .mapNotNull { withDocumentation(it) }

    private fun withDocumentation(section: DocumentationSection): DocumentationWithSection? {
        val documentation = documentationService.findById(section.documentationId) ?: return null
        return DocumentationWithSection(documentation, section)
    }
}

data class DocumentationWithSection(private val documentation: Documentation, private val section: DocumentationSection) {
    fun getUri() = URI(documentation.uri.toString() + "#${section.markup.elementId}")
    fun getTitle() = documentation.service
}
