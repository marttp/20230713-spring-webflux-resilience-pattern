package dev.tpcoder.rating

import dev.tpcoder.ProductRatingDto
import dev.tpcoder.ReviewDto
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service


@Service
class RatingService {

    private lateinit var map: Map<Int, ProductRatingDto>

    @PostConstruct
    private fun init() {

        // product 1
        val ratingDto1 = ProductRatingDto(
            4.5,
            listOf(
                ReviewDto("firstName1", "lastName1", 1, 5, "excellent"),
                ReviewDto("firstName2", "lastName2", 1, 4, "decent")
            )
        )

        // product 2
        val ratingDto2 = ProductRatingDto(
            4.0,
            listOf(
                ReviewDto("firstName3", "lastName3", 2, 5, "best"),
                ReviewDto("firstName4", "lastName4", 2, 3, "")
            )
        )

        // map as db
        map = mapOf(
            1 to ratingDto1,
            2 to ratingDto2
        )
    }

    fun getRatingForProduct(productId: Int): ProductRatingDto {
        return map[productId] ?: ProductRatingDto(0.0, emptyList())
    }
}