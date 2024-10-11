package ch.silberruecken.das

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@Import(TestcontainersConfiguration::class)
@SpringBootTest
class DocAggregatorServiceApplicationTests {

    @Test
    fun contextLoads() {
    }

}
