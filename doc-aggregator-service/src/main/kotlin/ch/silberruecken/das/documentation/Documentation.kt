package ch.silberruecken.das.documentation

import ch.silberruecken.das.documentation.mongodb.DocumentationRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.net.URI

@Document
data class Documentation(val id: DocumentationId?, val type: DocumentationType, val service: String, @field:Indexed(unique = true) val uri: URI, val access: DocumentationAccess) {
    fun createOrUpdate(documentationRepository: DocumentationRepository, eventPublisher: ApplicationEventPublisher): Documentation {
        val doc = documentationRepository.findByUri(uri) ?: documentationRepository.save(this)
        eventPublisher.publishEvent(DocumentationUpdated(doc))
        return doc
    }
}

enum class DocumentationType { API, ARCHITECTURE }
enum class DocumentationAccess { PUBLIC, PROTECTED }

@JvmInline
value class DocumentationId(val id: String)
