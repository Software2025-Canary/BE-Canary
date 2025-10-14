package sw2025.canary

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CanaryApplication

fun main(args: Array<String>) {
	runApplication<CanaryApplication>(*args)
}
