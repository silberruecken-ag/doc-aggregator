package ch.silberruecken.docsampleservice

import org.springframework.boot.fromApplication

fun main(args: Array<String>) {
    fromApplication<DocSampleServiceApplication>()
        .run(*args)
}
