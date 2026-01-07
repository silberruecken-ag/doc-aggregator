package ch.silberruecken.das.shared.security.utils

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder

object InternalAuthentication {
    fun runWithScope(scope: String, function: () -> Unit) {
        val oldContext = SecurityContextHolder.getContext()
        val newContext = SecurityContextHolder.createEmptyContext().also {
            it.authentication = UsernamePasswordAuthenticationToken("internal user", null, listOf(SimpleGrantedAuthority(scope)))
        }
        SecurityContextHolder.setContext(newContext)
        function()
        SecurityContextHolder.setContext(oldContext)
    }
}
