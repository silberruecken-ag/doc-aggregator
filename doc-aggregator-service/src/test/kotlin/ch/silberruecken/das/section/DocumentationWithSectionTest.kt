package ch.silberruecken.das.section

import ch.silberruecken.das.documentation.Documentation
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Answers
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.net.URI

class DocumentationWithSectionTest {
    private val baseUrl = URI("http://test")

    @Test
    fun `should be valid uri`() {
        val documentation = mock<Documentation> {
            on { uri } doReturn baseUrl
        }
        val section = Mockito.mock(DocumentationSection::class.java, Answers.RETURNS_DEEP_STUBS)
        whenever(section.markup.elementId) doReturn "my id"
        val testee = DocumentationWithSection(documentation, section, "summary")
        assertThat(testee.getUri().toString()).isEqualTo("$baseUrl#my%20id")
    }
}
