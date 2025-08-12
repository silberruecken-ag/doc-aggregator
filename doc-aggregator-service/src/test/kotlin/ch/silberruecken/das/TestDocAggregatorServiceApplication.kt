package ch.silberruecken.das

import org.springframework.boot.fromApplication
import org.springframework.boot.with
import tech.chrigu.spring.templateeditor.TemplateEditorConfiguration

fun main(args: Array<String>) {
    fromApplication<DocAggregatorServiceApplication>()
        .with(TestcontainersConfiguration::class)
        .with(TemplateEditorConfiguration::class)
        .run(*args)
}
