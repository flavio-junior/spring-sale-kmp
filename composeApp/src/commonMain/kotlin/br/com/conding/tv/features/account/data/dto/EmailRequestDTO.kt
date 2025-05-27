package br.com.conding.tv.features.account.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmailRequestDTO(
    @SerialName(value = "email")
    val email: String
)
