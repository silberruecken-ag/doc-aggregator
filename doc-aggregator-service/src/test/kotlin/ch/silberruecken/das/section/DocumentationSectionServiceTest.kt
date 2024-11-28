package ch.silberruecken.das.section

import ch.silberruecken.das.TestcontainersConfiguration
import ch.silberruecken.das.documentation.Documentation
import ch.silberruecken.das.documentation.DocumentationId
import ch.silberruecken.das.documentation.DocumentationService
import ch.silberruecken.das.section.elasticsearch.SectionIndexRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.boot.test.autoconfigure.data.elasticsearch.DataElasticsearchTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.bean.override.mockito.MockitoBean
import java.net.URI

@DataElasticsearchTest
@Import(DocumentationSectionService::class, TestcontainersConfiguration.ElasticsearchContainerConfiguration::class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class DocumentationSectionServiceTest(
    private val repository: SectionIndexRepository, private val testee: DocumentationSectionService,
    @MockitoBean private val documentationService: DocumentationService
) {
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

    private val documentationId = DocumentationId("1")

    @BeforeEach
    fun initData() {
        repository.deleteAll()
        repository.saveAll(
            listOf(
                DocumentationSection(null, documentationId, SectionMarkup(null, 0, body)),
                DocumentationSection(null, documentationId, SectionMarkup("heading", 1, heading)),
                DocumentationSection(null, documentationId, SectionMarkup("heading2", 2, heading2))
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

    @Test
    fun `should find documents`() {
        val baseUri = "http://bla.bla/api.html"
        val title = "bla-service"
        val doc = mock<Documentation> {
            on { uri } doReturn URI(baseUri)
            on { service } doReturn title
        }
        whenever(documentationService.findById(documentationId)) doReturn doc
        val result = testee.findDocsByQuery("text")
        assertThat(result).hasSize(1)
        assertThat(result[0].getUri().toString()).isEqualTo("$baseUri#heading")
        assertThat(result[0].getTitle()).isEqualTo(title)
    }
}
