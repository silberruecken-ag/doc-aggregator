package ch.silberruecken.docsampleservice.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class MainController {
    @RequestMapping("/index")
    fun index() = ModelAndView("index", mapOf("message" to "Hello World"))
}
