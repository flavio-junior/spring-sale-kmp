package br.com.conding.tv.features.settings.data.vo

internal data class UserResponseVO(
    var id: Long? = 0,
    var name: String? = "",
    var surname: String? = "",
    var username: String? = "",
    var email: String? = null
)
