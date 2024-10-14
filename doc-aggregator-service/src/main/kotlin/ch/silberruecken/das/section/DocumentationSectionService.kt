package ch.silberruecken.das.section

import ch.silberruecken.das.section.elasticsearch.SectionIndexRepository
import org.springframework.stereotype.Service

@Service
class DocumentationSectionService(private val sectionIndexRepository: SectionIndexRepository) {
    fun findByQuery(query: String) = sectionIndexRepository.findByMarkupHtmlContainingOrderByMarkupElementDepthDesc(query)
}
