package ch.silberruecken.das

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.modulith.Modulithic

@Modulithic(sharedModules = ["shared"])
@SpringBootApplication
class DocAggregatorServiceApplication

fun main(args: Array<String>) {
    runApplication<DocAggregatorServiceApplication>(*args)
}
