package br.com.conding.tv.components.mask

import kotlin.math.round

internal fun formatterMaskToMoney(price: Double): String {
    val currencySymbol = "R$"
    val numericValue = price
        .toString()
        .replace("[^\\d.]".toRegex(), replacement = "")
        .toDoubleOrNull() ?: 0.0
    val formattedValue = formatNumberWithThousandsSeparator(numericValue)
    return "$currencySymbol $formattedValue"
}

private fun formatNumberWithThousandsSeparator(value: Double): String {
    val roundedValue = round(x = value * 100) / 100
    val parts = roundedValue.toString().split(".")
    val integerPart = parts[0].reversed().chunked(size = 3).joinToString(separator = ",").reversed()
    val decimalPart = if (parts.size > 1) parts[1].padEnd(length = 2, padChar = '0') else "00"
    return "$integerPart.$decimalPart"
}
