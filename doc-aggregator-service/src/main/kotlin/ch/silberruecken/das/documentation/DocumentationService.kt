package ch.silberruecken.das.documentation

import ch.silberruecken.das.documentation.mongodb.DocumentationRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class DocumentationService(
    private val documentationRepository: DocumentationRepository,
    private val eventPublisher: ApplicationEventPublisher
) {
    fun createOrUpdateDocumentation(documentation: Documentation): Documentation {
        return documentation.createOrUpdate(documentationRepository, eventPublisher)
    }
}
