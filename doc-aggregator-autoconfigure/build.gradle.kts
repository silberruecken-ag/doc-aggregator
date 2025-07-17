dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.4.4"))

    compileOnly("org.springframework.boot:spring-boot-starter")
    compileOnly("org.springframework:spring-web")

    // Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
