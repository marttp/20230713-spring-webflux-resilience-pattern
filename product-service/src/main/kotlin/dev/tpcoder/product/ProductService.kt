package dev.tpcoder.product

import dev.tpcoder.ProductDto
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono


@Service
class ProductService(private val ratingServiceClient: RatingServiceClient) {

    private lateinit var map: Map<Int, Product>

    @PostConstruct
    private fun init() {
        map = mapOf(
            1 to Product(1, "Blood On The Dance Floor", 12.45),
            2 to Product(2, "The Eminem Show", 12.12)
        )
    }

    fun getProductDto(productId: Int): Mono<ProductDto> {
        return ratingServiceClient.getProductRatingDto(1)
            .flatMap { productRatingDto ->
                val product = map[productId]
                val dto = ProductDto(productId, product!!.description, product.price, productRatingDto)
                Mono.just(dto)
            }
    }
}