package ch.silberruecken.das.documentation.testdata

import ch.silberruecken.das.documentation.Documentation
import ch.silberruecken.das.documentation.DocumentationAccess
import ch.silberruecken.das.documentation.DocumentationService
import ch.silberruecken.das.documentation.DocumentationType
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.net.URI

@Profile("testdata")
@Component
class DocumentationSectionTestDataGenerator(private val documentationService: DocumentationService) : ApplicationRunner {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun run(args: ApplicationArguments) {
        logger.info("Init test data...")
        documentationService.createOrUpdateDocumentation(Documentation(null, DocumentationType.API, "silberruecken", URI("https://silberruecken.ch"), DocumentationAccess.PUBLIC))
        documentationService.createOrUpdateDocumentation(
            Documentation(
                null,
                DocumentationType.API,
                "spring",
                URI("https://docs.spring.io/spring-framework/reference/overview.html"),
                DocumentationAccess.PUBLIC
            )
        )
        logger.info("Test data initialized")
    }
}
