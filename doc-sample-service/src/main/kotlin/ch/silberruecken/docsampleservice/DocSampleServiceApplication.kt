package ch.silberruecken.docsampleservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DocSampleServiceApplication

fun main(args: Array<String>) {
    runApplication<DocSampleServiceApplication>(*args)
}
