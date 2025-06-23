package net.elau.example.spring_boot_opentelemetry

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class SpringBootOpentelemetryApplication

fun main(args: Array<String>) {
	runApplication<SpringBootOpentelemetryApplication>(*args)
}
