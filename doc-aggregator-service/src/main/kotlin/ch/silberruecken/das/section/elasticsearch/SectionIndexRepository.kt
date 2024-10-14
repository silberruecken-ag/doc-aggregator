package ch.silberruecken.das.section.elasticsearch

import ch.silberruecken.das.documentation.DocumentationId
import ch.silberruecken.das.section.DocumentationSection
import co.elastic.clients.elasticsearch._types.SortOrder
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate
import org.springframework.data.elasticsearch.client.elc.NativeQuery
import org.springframework.data.elasticsearch.core.SearchHits
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository


interface SectionIndexRepository : ElasticsearchRepository<DocumentationSection, String>, CustomSectionRepository {
    fun deleteByDocumentationId(documentationId: DocumentationId)
}

interface CustomSectionRepository {
    fun findByMarkupHtmlContainingOrderByMarkupElementDepthDesc(searchTerm: String): SearchHits<DocumentationSection>
}

class CustomSectionRepositoryImpl(private val elasticsearchTemplate: ElasticsearchTemplate) : CustomSectionRepository {
    override fun findByMarkupHtmlContainingOrderByMarkupElementDepthDesc(searchTerm: String): SearchHits<DocumentationSection> {
        val query = NativeQuery.builder()
            .withQuery { q ->
                q.nested { n ->
                    n.path("markup")
                        .query { innerQ ->
                            innerQ.match { m ->
                                m.field("markup.html").query(searchTerm)
                            }
                        }
                }
            }
            .withSort { s ->
                s.field { f ->
                    f.field("markup.elementDepth")
                        .order(SortOrder.Desc)
                        .nested { n ->
                            n.path("markup")
                        }
                }
            }
            .build()

        return elasticsearchTemplate.search(query, DocumentationSection::class.java)
    }
}
