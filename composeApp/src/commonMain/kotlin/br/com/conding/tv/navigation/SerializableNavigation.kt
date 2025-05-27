package br.com.conding.tv.navigation

import kotlinx.serialization.Serializable

@Serializable
data class CheckCodeToConfirmEmail(val email: String)

@Serializable
data class SignUp(val email: String)
