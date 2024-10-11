package ch.silberruecken.das.section.elasticsearch

import ch.silberruecken.das.documentation.DocumentationId
import ch.silberruecken.das.section.DocumentationSection
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface SectionIndexRepository : ElasticsearchRepository<DocumentationSection, Long> {
    fun deleteByDocumentationId(documentationId: DocumentationId)
}
