package ch.silberruecken.das

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
    fromApplication<DocAggregatorServiceApplication>().with(TestcontainersConfiguration::class).run(*args)
}
