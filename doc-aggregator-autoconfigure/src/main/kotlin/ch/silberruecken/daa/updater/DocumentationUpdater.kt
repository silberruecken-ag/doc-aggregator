package ch.silberruecken.daa.updater

import ch.silberruecken.daa.Documentation
import ch.silberruecken.daa.client.DocAggregatorClient
import ch.silberruecken.daa.client.DocAggregatorProperties
import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener

class DocumentationUpdater(
    private val docAggregatorClient: DocAggregatorClient,
    private val docAggregatorProperties: DocAggregatorProperties
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @EventListener(ApplicationReadyEvent::class)
    fun onApplicationReady() {
        try {
            logger.info("Update documentation...")
            docAggregatorProperties.getDocumentationWithFullUrls().forEach { updateDocumentation(it) }
        } catch (e: Exception) {
            logger.error("Failed to update documentation", e)
        }
    }

    private fun updateDocumentation(documentation: Documentation) {
        docAggregatorClient.updateDocumentation(documentation)
        logger.info("Documentation $documentation updated")
    }
}
