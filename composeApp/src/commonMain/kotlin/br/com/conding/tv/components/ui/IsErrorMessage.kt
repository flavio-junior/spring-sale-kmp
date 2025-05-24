package br.com.conding.tv.components.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun IsErrorMessage(
    isError: Boolean,
    message: String
) {
    if (isError && message.isNotEmpty()) {
        InfoText(modifier = Modifier.fillMaxWidth(), text = message, textAlign = TextAlign.Start)
    }
}
