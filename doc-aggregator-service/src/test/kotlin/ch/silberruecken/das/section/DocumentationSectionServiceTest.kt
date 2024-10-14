package ch.silberruecken.das.section

import ch.silberruecken.das.TestcontainersConfiguration
import ch.silberruecken.das.documentation.DocumentationId
import ch.silberruecken.das.section.elasticsearch.SectionIndexRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.data.elasticsearch.DataElasticsearchTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.TestConstructor

@DataElasticsearchTest
@Import(DocumentationSectionService::class, TestcontainersConfiguration.ElasticsearchContainerConfiguration::class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class DocumentationSectionServiceTest(private val repository: SectionIndexRepository, private val testee: DocumentationSectionService) {
    private val heading2 = """<div id="heading2">
            <h2>Title 2</h2>
          </div>"""
    private val heading = """<div id="heading">
          <h1>Title</h1>
          <p>Text</p>
          $heading2
        </div>"""
    private val body = """
        <p>Intro</p>
        $heading
    """.trimIndent()

    @BeforeEach
    fun initData() {
        repository.deleteAll()
        repository.saveAll(
            listOf(
                DocumentationSection(null, DocumentationId("1"), SectionMarkup(null, 0, body)),
                DocumentationSection(null, DocumentationId("1"), SectionMarkup("heading", 1, heading)),
                DocumentationSection(null, DocumentationId("1"), SectionMarkup("heading2", 2, heading2))
            )
        )
    }

    @Test
    fun `should find indexed sections`() {
        val result = testee.findByQuery("text")
        assertThat(result.totalHits).isEqualTo(2)
        assertThat(result.getSearchHit(0).content.markup.elementId).isEqualTo("heading")
        assertThat(result.getSearchHit(1).content.markup.elementId).isNull()
    }

    @Test
    fun `should ignore html elements`() {
        val result = testee.findByQuery("heading2")
        assertThat(result.totalHits).isEqualTo(0)
    }
}
