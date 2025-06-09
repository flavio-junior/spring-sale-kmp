package br.com.conding.tv.networking.resources

import kotlinx.serialization.Serializable

@Serializable
internal data class ResponseError(
    val status: Int,
    val message: String
)
