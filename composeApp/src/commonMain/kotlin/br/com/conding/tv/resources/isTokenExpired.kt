package br.com.conding.tv.resources

internal expect fun isTokenExpired(expirationDate: String): Boolean
