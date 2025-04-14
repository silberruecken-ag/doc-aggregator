package ch.silberruecken.das

import ch.silberruecken.das.documentation.DocumentationId
import ch.silberruecken.das.section.DocumentationSection
import ch.silberruecken.das.section.DocumentationSectionService
import ch.silberruecken.das.section.SectionMarkup
import ch.silberruecken.das.section.elasticsearch.SectionIndexRepository
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.assertj.core.api.Assertions.assertThat


class StepDefinitions(
    private val sectionIndexRepository: SectionIndexRepository,
    private val documentationSectionService: DocumentationSectionService
) {
    private lateinit var query: String

    @Given("a documentation with text {string}")
    fun a_documentation_with_text(text: String) {
        sectionIndexRepository.save(DocumentationSection(null, DocumentationId("1"), SectionMarkup(null, 0, text)))
    }

    @When("a user searches for a documentation with query {string}")
    fun a_user_searches_for_a_documentation_with_query(query: String) {
        this.query = query
    }

    @Then("he should get one result")
    fun he_should_get_one_result() {
        val result = documentationSectionService.findByQuery(query)
        assertThat(result).hasSize(1)
    }
}