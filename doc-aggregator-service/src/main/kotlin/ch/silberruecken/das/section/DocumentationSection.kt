package ch.silberruecken.das.section

import ch.silberruecken.das.documentation.DocumentationId
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import org.springframework.data.elasticsearch.annotations.Setting

@Document(indexName = "sections")
@Setting(settingPath = "es-settings.json")
data class DocumentationSection(val id: String?, val documentationId: DocumentationId, @Field(type = FieldType.Nested) val markup: SectionMarkup)

// TODO: Stemming per language (yet html is probably indexed as english)
data class SectionMarkup(val elementId: String?, val elementDepth: Int, @Field(type = FieldType.Text, analyzer = "html_analyzer") val html: String)
