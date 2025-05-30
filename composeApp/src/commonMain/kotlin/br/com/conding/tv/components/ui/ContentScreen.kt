package br.com.conding.tv.components.ui

import androidx.compose.runtime.Composable

@Composable
expect fun ContentScreen(
    label: String? = null,
    goToBackScreen: () -> Unit = {},
    extra: @Composable (Boolean) -> Unit = {},
    content: @Composable () -> Unit = {}
)
