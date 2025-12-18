package ch.silberruecken.daa

import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.beans.factory.UnsatisfiedDependencyException
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer
import org.springframework.boot.diagnostics.FailureAnalysis
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.web.client.RestClient

@Order(Ordered.HIGHEST_PRECEDENCE)
class RestClientBuilderMissingFailureAnalyzer : AbstractFailureAnalyzer<UnsatisfiedDependencyException>() {
    override fun analyze(rootFailure: Throwable, cause: UnsatisfiedDependencyException): FailureAnalysis? {
        val noSuchBeanDefinition = cause.cause
        if (noSuchBeanDefinition is NoSuchBeanDefinitionException && noSuchBeanDefinition.beanType == RestClient.Builder::class.java) {
            return FailureAnalysis(
                "The RestClient.Builder bean is missing, which is required for the doc-aggregator autoconfiguration.",
                "Add the 'spring-boot-starter-restclient' dependency to your build file (Spring Boot 4+).",
                cause
            )
        }
        return null
    }
}