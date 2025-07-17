
description = "kotlin-application-parent"

plugins {
    `kotlin-dsl`
    id("io.spring.dependency-management") version "1.1.7"
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.25")
    implementation("org.jetbrains.kotlin:kotlin-allopen:1.9.25")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:3.4.3")
}

repositories {
    mavenCentral()
}
