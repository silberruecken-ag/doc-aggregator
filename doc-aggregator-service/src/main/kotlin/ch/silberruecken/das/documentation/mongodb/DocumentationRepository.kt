package ch.silberruecken.das.documentation.mongodb

import ch.silberruecken.das.documentation.Documentation
import org.springframework.data.mongodb.repository.MongoRepository
import java.net.URI
import java.util.*

interface DocumentationRepository : MongoRepository<Documentation, UUID> {
    fun findByUri(uri: URI): Documentation?
}
