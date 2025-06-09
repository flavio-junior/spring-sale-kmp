package br.com.conding.tv.resources

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

internal actual fun isTokenExpired(expirationDate: String): Boolean {
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    val expirationInstance = LocalDateTime.parse(expirationDate, formatter)
    val currentDate = LocalDateTime.now()
    return currentDate.isAfter(expirationInstance)
}
