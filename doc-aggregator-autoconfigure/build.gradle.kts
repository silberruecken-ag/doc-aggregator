import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    compileOnly("org.springframework.boot:spring-boot-starter")
    compileOnly("org.springframework.boot:spring-boot-starter-webmvc")
    compileOnly("org.springframework.boot:spring-boot-starter-security-oauth2-client")

    testImplementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc")
    testImplementation("org.springframework.boot:spring-boot-starter-security-oauth2-client")

    // Custom dependencies
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.4.0")

    // Project dependencies
    implementation(project(":doc-aggregator-shared"))
}

tasks.withType<BootJar> {
    enabled = false
}
