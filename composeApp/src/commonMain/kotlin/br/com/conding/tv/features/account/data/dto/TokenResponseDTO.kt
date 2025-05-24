package br.com.conding.tv.features.account.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenResponseDTO(
    val expiration: String,
    @SerialName(value = "access_token")
    val accessToken: String,
    @SerialName(value = "refresh_token")
    val refreshToken: String
)
