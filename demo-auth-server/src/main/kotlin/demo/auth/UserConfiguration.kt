package demo.auth

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.User
import org.springframework.security.provisioning.InMemoryUserDetailsManager

@Configuration
class UserConfiguration {
    @Bean
    fun userDetailsService() = InMemoryUserDetailsManager(
        user("user", "USER"),
        user("admin", "USER", "ADMIN")
    )

    private fun user(username: String, vararg roles: String) = User.withDefaultPasswordEncoder().username(username).password(username).roles(*roles).build()
}
