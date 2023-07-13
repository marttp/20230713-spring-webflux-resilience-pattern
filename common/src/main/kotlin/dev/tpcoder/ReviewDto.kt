package dev.tpcoder

data class ReviewDto(
    val userFirstname: String,
    val userLastname: String,
    val productId: Int,
    val rating: Int,
    val comment: String
)
