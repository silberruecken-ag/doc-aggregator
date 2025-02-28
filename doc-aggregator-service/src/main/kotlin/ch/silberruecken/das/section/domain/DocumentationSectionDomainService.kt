package ch.silberruecken.das.section.domain

import ch.silberruecken.das.documentation.DocumentationId
import ch.silberruecken.das.documentation.DocumentationUpdated
import ch.silberruecken.das.section.DocumentationSection
import ch.silberruecken.das.section.SectionMarkup
import ch.silberruecken.das.section.elasticsearch.SectionIndexRepository
import ch.silberruecken.das.section.html.HtmlParser
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.modulith.events.ApplicationModuleListener
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Service
class DocumentationSectionDomainService(private val restClient: RestClient, private val sectionIndexRepository: SectionIndexRepository) {
    @ApplicationModuleListener
    fun updateSections(event: DocumentationUpdated) {
        val documentation = event.documentation
        require(documentation.id != null) { "Sections can only be updated for persisted documentations" }
        val html = restClient.get().uri(documentation.uri).retrieve().body<String>() ?: ""
        val document = Jsoup.parse(html)
        val sections = document.toSections(documentation.id)
        sectionIndexRepository.deleteByDocumentationId(documentation.id)
        sectionIndexRepository.saveAll(sections)
    }

    private fun Document.toSections(documentationId: DocumentationId) = HtmlParser.sectionsByIds(body())
        .map { DocumentationSection(null, documentationId, SectionMarkup(it.elementId, it.depth, it.element.text())) }
}