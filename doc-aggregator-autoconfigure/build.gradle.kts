import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    compileOnly("org.springframework.boot:spring-boot-starter")
    compileOnly("org.springframework.boot:spring-boot-starter-webmvc")

    testImplementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc")

    // Project dependencies
    implementation(project(":doc-aggregator-shared"))
}

tasks.withType<BootJar> {
    enabled = false
}
