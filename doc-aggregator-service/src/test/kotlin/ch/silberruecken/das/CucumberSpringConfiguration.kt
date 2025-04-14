package ch.silberruecken.das

import io.cucumber.spring.CucumberContextConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@CucumberContextConfiguration
@SpringBootTest
@Import(TestcontainersConfiguration::class)
class CucumberSpringConfiguration