package ch.silberruecken.das.section

import ch.silberruecken.das.documentation.DocumentationId
import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = "sections")
data class DocumentationSection(val id: Long?, val documentationId: DocumentationId, val markup: SectionMarkup)
data class SectionMarkup(val elementId: String?, val elementDepth: Int, val html: String)