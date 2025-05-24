package br.com.conding.tv.features.account.data.dto

import br.com.conding.tv.features.account.domain.type.TypeAccount
import kotlinx.serialization.Serializable

@Serializable
data class TokenResponseDTO(
    val user: String,
    val authenticated: Boolean,
    val created: String,
    val type: TypeAccount,
    val expiration: String,
    val accessToken: String,
    val refreshToken: String,
)
