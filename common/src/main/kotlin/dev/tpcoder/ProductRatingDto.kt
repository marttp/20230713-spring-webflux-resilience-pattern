package dev.tpcoder


data class ProductRatingDto(
    val avgRating: Double,
    val reviews: List<ReviewDto>
)