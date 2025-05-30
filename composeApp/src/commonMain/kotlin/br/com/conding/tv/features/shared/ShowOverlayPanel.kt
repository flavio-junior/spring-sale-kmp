package br.com.conding.tv.features.shared

import androidx.compose.runtime.Composable

@Composable
expect fun ShowOverlayPanel(
    onCloseProgram: () -> Unit = {},
    onChangeAccountRequest: () -> Unit = {},
    onDismissRequest: () -> Unit = {}
)
