package ch.silberruecken.das.section.html

import org.assertj.core.api.Assertions
import org.jsoup.Jsoup
import org.junit.jupiter.api.Test

class HtmlParserTest {
    @Test
    fun `should split html body into sections starting with an anchor tag`() {
        val result = HtmlParser.sectionsByIds(html.body())
        Assertions.assertThat(result).hasSize(7)
        Assertions.assertThat(result[0].depth).isEqualTo(0)
        Assertions.assertThat(result.drop(1).map { it.elementId to it.depth }).containsExactlyInAnyOrder(
            "content" to 1,
            "_hypermedia" to 3,
            "_errors" to 3,
            "_400_bad_request" to 5,
            "_500_internal_server_error" to 5,
            "_503_service_unavailable" to 5
        )
    }

    private val html = Jsoup.parse(
        """
    <html lang="en">
<head></head>
<body>
<h1>My API</h1>
Introduction ...
<p>TOC</p>
<div id="content">
    <div class="sect1">
        <h2 id="_hypermedia">Hypermedia</h2>
        <div class="sectionbody">
            <div class="paragraph">
                <p>Every resource is represented as HAL entity or collection.
                    You can get an overview of all resources by
                    <code>GET /</code>.</p>
            </div>
            <div class="admonitionblock warning">
                <table>
                    <tbody>
                    <tr>
                        <td class="icon">
                            <div class="title">Warning</div>
                        </td>
                        <td class="content">
                            Work in progress
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="sect1">
        <h2 id="_errors">Errors</h2>
        <div class="sectionbody">
            <div class="paragraph">
                <p>All error responses have a meaningful status code plus a consistent response body.
                    Special responses with additional response body properties are mentioned in the following.</p>
            </div>
            <div class="sect2">
                <h3 id="_400_bad_request">400 Bad Request</h3>
                <div class="paragraph">
                    <p>There are two special cases:</p>
                </div>
                <div class="ulist">
                    <ul>
                        <li>
                            <p><strong>Constraint violations</strong>: Contains one or multiple <code>errors</code>.</p>
                        </li>
                        <li>
                            <p><strong>Business exceptions</strong>: May contain additional properties like ...</p>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="sect2">
                <h3 id="_500_internal_server_error">500 Internal Server Error</h3>
                <div class="paragraph">
                    <p>This is an unexpected error and therefore a bug.</p>
                </div>
            </div>
            <div class="sect2">
                <h3 id="_503_service_unavailable">503 Service Unavailable</h3>
                <div class="paragraph">
                    <p>Most likely SAP is not available.</p>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
    """.trimIndent()
    )
}
