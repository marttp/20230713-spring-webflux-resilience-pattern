package dev.tpcoder.longcomputeservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LongComputeServiceApplication

fun main(args: Array<String>) {
	runApplication<LongComputeServiceApplication>(*args)
}
