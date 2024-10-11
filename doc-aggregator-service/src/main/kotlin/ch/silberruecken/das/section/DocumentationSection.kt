package ch.silberruecken.das.section

import ch.silberruecken.das.documentation.DocumentationId
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.Setting

@Document(indexName = "sections")
@Setting(settingPath = "es-settings.json")
data class DocumentationSection(val id: Long?, val documentationId: DocumentationId, @field:Field val markup: SectionMarkup)
data class SectionMarkup(val elementId: String?, val elementDepth: Int, @field:Field(analyzer = "html_analyzer") val html: String)
