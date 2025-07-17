import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("shared-conventions")
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.4.4"))

    compileOnly("org.springframework.boot:spring-boot-starter")
    compileOnly("org.springframework:spring-web")
}

tasks.withType<BootJar> {
    enabled = false
}
