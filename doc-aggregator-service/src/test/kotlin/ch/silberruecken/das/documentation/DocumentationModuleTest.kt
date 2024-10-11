package ch.silberruecken.das.documentation

import ch.silberruecken.das.TestcontainersConfiguration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.modulith.test.ApplicationModuleTest
import org.springframework.modulith.test.AssertablePublishedEvents
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@ApplicationModuleTest
@Import(TestcontainersConfiguration.MongoDbContainerConfiguration::class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@AutoConfigureMockMvc
class DocumentationModuleTest(private val mvc: MockMvc) {
    private val uri = "https://my-service.io/docs/index.html"
    private val service = "my-service"

    @Test
    fun `should persist documentation and trigger indexing`(events: AssertablePublishedEvents) {
        mvc.post("/documentations") {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                "uri": "$uri",
                "type": "API",
                "service": "$service"
                }
            """.trimIndent()
        }.andExpect {
            status { isCreated() }
            header { exists("Location") }
            jsonPath("id") { isNotEmpty() }
            jsonPath("uri") { value(uri) }
            jsonPath("uri") { value(uri) }
            jsonPath("type") { value("API") }
            jsonPath("service") { value(service) }
            jsonPath("access") { value("PUBLIC") }
        }

        assertThat(events).contains(DocumentationUpdated::class.java)
            .matching({ it.documentation.uri.toString() }, uri)
    }
}
