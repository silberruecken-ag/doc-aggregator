import org.gradle.kotlin.dsl.withType
import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
}

tasks.withType<BootJar> {
    enabled = false
}