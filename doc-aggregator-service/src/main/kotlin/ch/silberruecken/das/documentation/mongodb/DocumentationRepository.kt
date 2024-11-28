package ch.silberruecken.das.documentation.mongodb

import ch.silberruecken.das.documentation.Documentation
import org.springframework.data.mongodb.repository.MongoRepository
import java.net.URI

interface DocumentationRepository : MongoRepository<Documentation, String> {
    fun findByUri(uri: URI): Documentation?
}
