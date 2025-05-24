package br.com.conding.tv.features.account.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class SignInRequestDTO(
    val email: String,
    val password: String
)
