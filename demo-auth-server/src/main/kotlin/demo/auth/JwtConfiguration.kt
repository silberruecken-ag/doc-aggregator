package demo.auth

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer

/**
 * Adds a "roles" claim with all spring security roles
 */
@Configuration
class JwtConfiguration {
    @Bean
    fun jwtTokenCustomizer(): OAuth2TokenCustomizer<JwtEncodingContext> {
        return OAuth2TokenCustomizer { context ->
            val principal = context.getPrincipal<Authentication>()

            val roles = principal.authorities
                .mapNotNull { it.authority }
                .filter { it.startsWith("ROLE_") }
                .map { it.replace("ROLE_", "") }

            context.claims.claim("roles", roles)
        }
    }
}