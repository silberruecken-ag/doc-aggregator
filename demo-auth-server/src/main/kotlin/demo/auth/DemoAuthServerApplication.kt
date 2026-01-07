package demo.auth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DemoAuthServerApplication

fun main(args: Array<String>) {
    runApplication<DemoAuthServerApplication>(*args)
}
