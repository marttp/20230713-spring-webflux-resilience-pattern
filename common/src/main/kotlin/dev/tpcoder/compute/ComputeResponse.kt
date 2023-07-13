package dev.tpcoder.compute


data class ComputeResponse(
    val input: Long,
    val output: Long,
    val responseType: ResponseType,
    val message: String?
)
