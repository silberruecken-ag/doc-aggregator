plugins {
    `kotlin-dsl`
}
repositories {
    mavenCentral()
}
dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.25")
    implementation("org.jetbrains.kotlin:kotlin-allopen:1.9.25")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:3.4.3")
}