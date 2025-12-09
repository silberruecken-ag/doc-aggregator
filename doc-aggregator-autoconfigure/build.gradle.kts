import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    compileOnly("org.springframework.boot:spring-boot-starter")
    compileOnly("org.springframework:spring-web")
     testImplementation("org.springframework:spring-web")
}

tasks.withType<BootJar> {
    enabled = false
}
