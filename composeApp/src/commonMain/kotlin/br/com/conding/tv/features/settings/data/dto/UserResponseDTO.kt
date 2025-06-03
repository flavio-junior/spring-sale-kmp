package br.com.conding.tv.features.settings.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponseDTO(
    @SerialName(value = "id")
    var id: Long? = 0,
    @SerialName(value = "name")
    var name: String? = null,
    @SerialName(value = "surname")
    var surname: String? = null,
    @SerialName(value = "username")
    var username: String? = null,
    @SerialName(value = "email")
    var email: String? = null
)
