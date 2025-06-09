package br.com.conding.tv.features.account.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PasswordRequestDTO(
    @SerialName(value = "email")
    var email: String,
    @SerialName(value = "password")
    var password: String
)
