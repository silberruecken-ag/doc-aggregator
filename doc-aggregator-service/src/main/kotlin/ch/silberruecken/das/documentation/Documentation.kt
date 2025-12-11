package ch.silberruecken.das.documentation

import ch.silberruecken.das.documentation.mongodb.DocumentationRepository
import org.slf4j.LoggerFactory
import org.springframework.data.annotation.Transient
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.net.URI

@Document
data class Documentation(
    val id: DocumentationId?,
    val type: DocumentationType,
    val service: String,
    @field:Indexed(unique = true) val uri: URI,
    val access: DocumentationAccess,
    val version: Version?
) {
    @Transient
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun createOrUpdate(documentationRepository: DocumentationRepository): Documentation {
        val old = documentationRepository.findByUri(uri)
        if (old?.version != null && old.version == version) {
            logger.info("Documentation $uri is already indexed for version ${version.value}. Skipping update.")
            return this
        }
        val update = old?.copy(version = version) ?: this
        return documentationRepository.save(update)
    }
}

enum class DocumentationType { API, ARCHITECTURE }
enum class DocumentationAccess { PUBLIC, PROTECTED }

@JvmInline
value class DocumentationId(val id: String) {
    override fun toString() = id
}

data class Version(val value: String) {
    override fun toString() = value
}
