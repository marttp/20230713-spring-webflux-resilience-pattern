package dev.tpcoder.rating

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RatingServiceApplication

fun main(args: Array<String>) {
	runApplication<RatingServiceApplication>(*args)
}
