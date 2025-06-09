package br.com.conding.tv.features.account.data.vo

internal data class TokenResponseVO(
    val expiration: String = "",
    val accessToken: String = "",
    val refreshToken: String = ""
)
