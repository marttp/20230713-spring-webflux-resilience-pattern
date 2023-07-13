package dev.tpcoder.product

import dev.tpcoder.compute.ComputeResponse
import dev.tpcoder.compute.ResponseType
import io.github.resilience4j.ratelimiter.annotation.RateLimiter
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Service
class ComputeServiceClient(
    @Qualifier("computeWebClient") private val computeWebClient: WebClient
) {

    @RateLimiter(name = "squareLimit", fallbackMethod = "squareErrorResponse")
    fun getComputeSquare(input: Long): Mono<ComputeResponse> {
        return computeWebClient.get()
            .uri { it.path("/square/{input}").build(input) }
            .retrieve()
            .onStatus({ response -> response.isError }) { clientResponse ->
                clientResponse.createException()
                    .doOnNext { logger.error(it.message) }
                    .flatMap { Mono.error(it) }
            }
            .bodyToMono(ComputeResponse::class.java)
    }

    fun squareErrorResponse(input: Long, throwable: Throwable): Mono<ComputeResponse> {
        return Mono.just(ComputeResponse(input, -1, ResponseType.FAILURE, throwable.message))
    }

    companion object {
        private val logger = LoggerFactory.getLogger(ComputeServiceClient::class.java)
    }
}