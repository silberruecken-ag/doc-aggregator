package ch.silberruecken.das

import org.springframework.boot.fromApplication
import org.springframework.boot.with
import tech.chrigu.spring.templateeditor.TemplateEditorConfiguration

fun main(args: Array<String>) {
    fromApplication<DocAggregatorServiceApplication>()
        .withAdditionalProfiles("testdata")
        .with(TestcontainersConfiguration::class)
        .with(TemplateEditorConfiguration::class).withAdditionalProfiles("template-editor")
        .run(*args)
}
