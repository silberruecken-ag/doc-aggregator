package ch.silberruecken.das.section.html

import org.jsoup.nodes.Element

object HtmlParser {
    fun sectionsByIds(body: Element): List<HtmlSection> = listOf(HtmlSection(body, null, 0)) + body.getElementsByAttribute("id")
        .map { HtmlSection(it, it.id(), it.parents().size - 1) }
}

/**
 * @param depth 0=body element, 1=level underneath the body element, ...
 */
data class HtmlSection(val element: Element, val elementId: String?, val depth: Int) {
    init {
        require(depth >= 0)
        if (depth == 0)
            require(elementId == null)
        else
            require(elementId != null)
    }
}
