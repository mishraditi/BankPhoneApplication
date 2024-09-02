package org.aditilearn.bank

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBankApplication

fun main(args: Array<String>) {
	runApplication<SpringBankApplication>(*args)
}
