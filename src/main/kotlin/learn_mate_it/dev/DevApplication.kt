package learn_mate_it.dev

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class DevApplication

fun main(args: Array<String>) {
	runApplication<DevApplication>(*args)
}
