package br.com.conding.tv.navigation

import kotlinx.serialization.Serializable

@Serializable
object SignIn

@Serializable
data class CheckRecoverToken(val email: String)

@Serializable
object SendRecoverToken

@Serializable
data class RecoverToken(val email: String)

@Serializable
data class CheckCodeToConfirmEmail(val email: String)

@Serializable
data class SignUp(val email: String)

@Serializable
object Home
