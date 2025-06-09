package br.com.conding.tv.features.account.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SignUpRequestDTO(
    @SerialName(value = "email")
    val email: String,
    @SerialName(value = "password")
    val password: String
)
