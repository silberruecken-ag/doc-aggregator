plugins {
    `kotlin-dsl`
}
repositories {
    mavenCentral()
}
dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.21")
    implementation("org.jetbrains.kotlin:kotlin-allopen:2.2.21")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:4.0.0")
}