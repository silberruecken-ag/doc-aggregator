package ch.silberruecken.das.section.web

import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.security.web.csrf.CsrfToken
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ModelAttribute

@TestConfiguration
class DummyCsrfConfiguration() {
    @Bean
    fun dummyCsrfControllerAdvice() = DummyCsrfControllerAdvice()

    @ControllerAdvice
    class DummyCsrfControllerAdvice {
        @ModelAttribute("_csrf")
        fun provideCsrf() = mock<CsrfToken> {
            on { parameterName } doReturn "csrf"
            on { token } doReturn "mock"
        }
    }
}
