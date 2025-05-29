package br.com.conding.tv.features.account.ui.view

import androidx.compose.runtime.Composable

@Composable
expect fun ContentAccount(
    label: String? = null,
    goToBackScreen: () -> Unit = {},
    extra: @Composable (Boolean) -> Unit = {},
    content: @Composable () -> Unit = {}
)
