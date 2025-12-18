dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("tools.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-restclient") // TODO: Document that this is needed. Improve error handling/autoconfig.
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // Project dependency
    implementation(project(":doc-aggregator-autoconfigure"))
}

tasks.register("prepareKotlinBuildScriptModel") {}