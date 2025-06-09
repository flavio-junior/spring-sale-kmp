package br.com.conding.tv.features.settings.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class UserRequestDTO(
    var name: String? = null,
    @SerialName(value = "surname")
    var surname: String? = null,
    @SerialName(value = "username")
    var username: String? = null
)
