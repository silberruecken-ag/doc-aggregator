package demo.public

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PublicDemoApplication // TODO: Protected documentation (2nd service), Auth Server (IAM)

fun main(args: Array<String>) {
    runApplication<PublicDemoApplication>(*args)
}