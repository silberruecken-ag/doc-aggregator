package ch.silberruecken.das.section

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.elasticsearch.DataElasticsearchTest

@DataElasticsearchTest
class DocumentationSectionServiceTest(@Autowired private val testee: DocumentationSectionService) {
    @Test
    fun `should find indexed sections`() {
        TODO()
    }
}