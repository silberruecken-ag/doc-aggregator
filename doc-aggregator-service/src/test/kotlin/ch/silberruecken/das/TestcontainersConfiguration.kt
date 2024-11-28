package ch.silberruecken.das

import org.springframework.boot.devtools.restart.RestartScope
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.elasticsearch.ElasticsearchContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration(proxyBeanMethods = false)
@Import(TestcontainersConfiguration.ElasticsearchContainerConfiguration::class, TestcontainersConfiguration.MongoDbContainerConfiguration::class)
class TestcontainersConfiguration {
    @TestConfiguration(proxyBeanMethods = false)
    class ElasticsearchContainerConfiguration {
        @RestartScope
        @Bean
        @ServiceConnection
        fun elasticsearchContainer(): ElasticsearchContainer {
            return ElasticsearchContainer("docker.elastic.co/elasticsearch/elasticsearch:8.15.0")
                .withEnv("xpack.security.enabled", "false")
                .withEnv("ES_JAVA_OPTS", "-Xms256m -Xmx512m -XX:MaxDirectMemorySize=536870912")
        }
    }

    @TestConfiguration(proxyBeanMethods = false)
    class MongoDbContainerConfiguration {
        @RestartScope
        @Bean
        @ServiceConnection
        fun mongoDbContainer(): MongoDBContainer {
            return MongoDBContainer(DockerImageName.parse("mongo:latest"))
        }
    }
}
