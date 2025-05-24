package br.com.conding.tv.features.account.data.vo

data class TokenResponseVO(
    val expiration: String = "",
    val accessToken: String = "",
    val refreshToken: String = ""
)
