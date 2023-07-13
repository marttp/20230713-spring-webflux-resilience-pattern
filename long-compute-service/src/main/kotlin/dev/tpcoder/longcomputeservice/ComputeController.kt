package dev.tpcoder.longcomputeservice

import dev.tpcoder.compute.ComputeResponse
import dev.tpcoder.compute.ResponseType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController


@RestController
class ComputeController {

    @GetMapping("/double/{input}")
    fun doubleValue(@PathVariable input: Long): ComputeResponse {
        return ComputeResponse(input, 2 * input, ResponseType.SUCCESS, "")
    }

    @GetMapping("/square/{input}")
    fun getValue(@PathVariable input: Long): ComputeResponse {
        return ComputeResponse(input, input * input, ResponseType.SUCCESS, "")
    }
}