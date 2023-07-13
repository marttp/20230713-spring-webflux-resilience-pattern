package dev.tpcoder.product

import dev.tpcoder.ProductDto
import dev.tpcoder.compute.ComputeResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers


@RestController
@RequestMapping("product")
class ProductController(private val productService: ProductService,private val computeServiceClient: ComputeServiceClient) {

    @GetMapping("{productId}")
    fun getProduct(@PathVariable productId: Int): Mono<ProductDto> {
        return this.productService.getProductDto(productId)
    }

    @GetMapping("/compute/{input}")
    fun getComputeResult(@PathVariable input: Long): Mono<ComputeResponse> {
        return this.computeServiceClient.getComputeSquare(input)
    }
}