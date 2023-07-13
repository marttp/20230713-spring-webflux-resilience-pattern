package dev.tpcoder.product

import dev.tpcoder.ProductRatingDto
import io.github.resilience4j.bulkhead.annotation.Bulkhead
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.retry.annotation.Retry
import io.github.resilience4j.timelimiter.annotation.TimeLimiter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers


@Service
class RatingServiceClient(
    @Qualifier("ratingWebClient") private val ratingWebClient: WebClient
) {

    @Retry(name = RATING_SERVICE, fallbackMethod = FALLBACK_METHOD)
    @CircuitBreaker(name = RATING_SERVICE, fallbackMethod = FALLBACK_METHOD)
    @TimeLimiter(name = RATING_SERVICE, fallbackMethod = FALLBACK_METHOD)
    @Bulkhead(name = RATING_SERVICE, fallbackMethod = FALLBACK_METHOD)
    fun getProductRatingDto(productId: Int): Mono<ProductRatingDto> {
        return ratingWebClient.get()
            .uri { it.path("/{productId}").build(productId) }
            .retrieve()
            .onStatus({ response -> response.isError }) { clientResponse ->
                clientResponse.createException()
                    .doOnNext { logger.error(it.message) }
                    .flatMap { Mono.error(it) }
            }
            .bodyToMono(ProductRatingDto::class.java)
    }

    private fun getDefault(productId: Int, throwable: Exception): Mono<ProductRatingDto> {
        logger.error("[DEFAULT] Error when calling rating service: ${throwable.message}")
        return Mono.just(
            ProductRatingDto(
                0.0,
                emptyList()
            )
        )
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(RatingServiceClient::class.java)
        private const val RATING_SERVICE = "rating-service"
        private const val FALLBACK_METHOD = "getDefault"
    }
}