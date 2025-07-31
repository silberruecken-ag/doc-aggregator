package ch.silberruecken.docsampleservice

import org.springframework.boot.fromApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.with
import org.springframework.context.annotation.Bean
import org.springframework.experimental.boot.server.exec.CommonsExecWebServerFactoryBean
import org.springframework.experimental.boot.test.context.DynamicProperty
import org.springframework.experimental.boot.test.context.EnableDynamicProperty

@TestConfiguration(proxyBeanMethods = false)
@EnableDynamicProperty
class TestDocSampleServiceApplication {
    @Bean
    @DynamicProperty(name = "das.uri", value = "'http://localhost:' + port + '/documentations'")
    fun dasService(): CommonsExecWebServerFactoryBean {
        return CommonsExecWebServerFactoryBean.builder()
            .mainClass("org.springframework.boot.loader.launch.JarLauncher")
            .classpath { it.files("doc-aggregator-service/build/libs/doc-aggregator-service-0.0.1-SNAPSHOT.jar") }
    }
}

fun main(args: Array<String>) {
    fromApplication<DocSampleServiceApplication>()
        .with(TestDocSampleServiceApplication::class)
        .run(*args)
}
