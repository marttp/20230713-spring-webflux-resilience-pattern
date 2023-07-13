package dev.tpcoder.rating

import dev.tpcoder.ProductRatingDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.time.temporal.ChronoUnit
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.TimeUnit


@RestController
@RequestMapping("ratings")
class RatingController(private val ratingService: RatingService) {

    @GetMapping("{productId}")
    fun getRating(@PathVariable productId: Int): Mono<ResponseEntity<ProductRatingDto>> {
        val productRatingDto = ratingService.getRatingForProduct(productId)
//        return failRandomlyCircuitBreaker(productRatingDto)
        return testTimeout(productRatingDto)
    }

    private fun testTimeout(productRatingDto: ProductRatingDto): Mono<ResponseEntity<ProductRatingDto>> {
        TimeUnit.of(ChronoUnit.MILLIS).sleep(ThreadLocalRandom.current().nextLong(1000, 10000))
        return Mono.just(ResponseEntity.ok(productRatingDto))
    }

    private fun failRandomlyRetry(productRatingDto: ProductRatingDto): Mono<ResponseEntity<ProductRatingDto>> {
        val random: Int = ThreadLocalRandom.current().nextInt(1, 4)
        logger.info("Random number: $random")
        if (random < 2) {
            return Mono.just(ResponseEntity.internalServerError().build())
        } else if (random < 3) {
            return Mono.just(ResponseEntity.badRequest().build())
        }
        return Mono.just(ResponseEntity.ok(productRatingDto))
    }

    private fun failRandomlyCircuitBreaker(productRatingDto: ProductRatingDto): Mono<ResponseEntity<ProductRatingDto>> {
        TimeUnit.of(ChronoUnit.MILLIS).sleep(100)
        val random = ThreadLocalRandom.current().nextInt(1, 10)
        return if (random < 9) {
            Mono.just(ResponseEntity.status(500).build())
        } else {
            Mono.just(ResponseEntity.ok(productRatingDto))
        }
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(RatingController::class.java)
    }
}