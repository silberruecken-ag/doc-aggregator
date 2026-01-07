package ch.silberruecken.das.shared.security

import ch.silberruecken.das.shared.security.constants.Scopes
import ch.silberruecken.dashared.client.DocAggregatorServiceApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfiguration {
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    fun apiSecurity(http: HttpSecurity): SecurityFilterChain {
        http {
            securityMatcher("/api/**")
            authorizeHttpRequests {
                authorize(DocAggregatorServiceApi.DOCUMENTATION_URL, hasAuthority(Scopes.DOCUMENTATIONS_WRITE))
                authorize(anyRequest, denyAll)
            }
            oauth2ResourceServer {
                jwt {}
            }
            csrf { disable() }
        }
        return http.build()
    }

    @Bean
    fun mvcSecurity(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeHttpRequests {
                authorize(anyRequest, permitAll) // TODO: Add security later (server security)
            }
        }
        return http.build()
    }
}
