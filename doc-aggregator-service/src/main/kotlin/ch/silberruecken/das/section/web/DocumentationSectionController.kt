package ch.silberruecken.das.section.web

import ch.silberruecken.das.section.DocumentationSectionService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping(DocumentationSectionController.REQUEST_MAPPING)
class DocumentationSectionController(private val documentationSectionService: DocumentationSectionService) {
    @GetMapping
    fun searchDocs(@RequestParam query: String?): ModelAndView {
        val docs = if (query == null)
            null
        else
            documentationSectionService.findDocsByQuery(query)
        return ModelAndView(
            "docs",
            mapOf(
                "docs" to docs,
                "query" to query
            )
        )
    }

    companion object {
        const val REQUEST_MAPPING = "/docs"
    }
}
