package br.com.conding.tv.components.mask

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import br.com.conding.tv.theme.NumbersUtils.NUMBER_THREE
import br.com.conding.tv.theme.NumbersUtils.NUMBER_TWO

internal class CurrencyVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = text.text
        val digitsOnly = originalText.filter { it.isDigit() }
        val value = digitsOnly.toDoubleOrNull()?.div(other = NUMBER_HUNDRED) ?: NUMBER_ZERO
        val formattedText = buildAnnotatedString {
            append(text = MONEY)
            withStyle(SpanStyle(color = Color.Black)) {
                val integerPart = value.toLong()
                val decimalPart = ((value - integerPart) * NUMBER_HUNDRED).toInt()
                val formattedInteger = formatNumberWithThousandsSeparator(integerPart)
                append("$formattedInteger,${decimalPart.toString().padStart(length = NUMBER_TWO, '0')}")
            }
        }
        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int = formattedText.length
            override fun transformedToOriginal(offset: Int): Int = originalText.length
        }
        return TransformedText(formattedText, offsetMapping)
    }

    private fun formatNumberWithThousandsSeparator(number: Long): String {
        return number.toString()
            .reversed()
            .chunked(size = NUMBER_THREE)
            .joinToString(separator = ".")
            .reversed()
    }

    companion object {
        const val NUMBER_ZERO = 0.0
        const val NUMBER_HUNDRED = 100
        const val MONEY = "R$ "
    }
}
