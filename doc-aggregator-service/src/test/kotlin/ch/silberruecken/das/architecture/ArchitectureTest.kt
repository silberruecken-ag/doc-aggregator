package ch.silberruecken.das.architecture

import ch.silberruecken.das.DocAggregatorServiceApplication
import org.junit.jupiter.api.Test
import org.springframework.modulith.core.ApplicationModules
import org.springframework.modulith.docs.Documenter

class ArchitectureTest {
    private val modules = ApplicationModules.of(DocAggregatorServiceApplication::class.java)

    @Test
    fun verifyArchitecture() {
        modules.verify()
    }

    @Test
    fun documentArchitecture() {
        Documenter(modules).writeDocumentation()
    }
}