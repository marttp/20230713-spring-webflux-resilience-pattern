package dev.tpcoder.product

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig(
    @Value("\${rating.service.endpoint}") private val ratingServiceUrl: String,
    @Value("\${compute.service.endpoint}") private val computeServiceUrl: String
) {

    @Bean
    fun ratingWebClient(): WebClient = WebClient.builder()
        .baseUrl(ratingServiceUrl)
        .build()

    @Bean
    fun computeWebClient(): WebClient = WebClient.builder()
        .baseUrl(computeServiceUrl)
        .build()
}