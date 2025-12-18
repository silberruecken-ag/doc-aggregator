package ch.silberruecken.das.documentation

import ch.silberruecken.das.documentation.mongodb.DocumentationRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

// TODO: Security
@Service
class DocumentationService(
    private val documentationRepository: DocumentationRepository,
    private val eventPublisher: ApplicationEventPublisher
) {
    @Transactional
    fun createOrUpdateDocumentation(documentation: Documentation): Documentation {
        return documentation.createOrUpdate(documentationRepository, eventPublisher)
    }

    fun findById(id: DocumentationId) = documentationRepository.findByIdOrNull(id.toString())
}
