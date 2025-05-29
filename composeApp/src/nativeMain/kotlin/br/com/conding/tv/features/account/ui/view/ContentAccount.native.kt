package br.com.conding.tv.features.account.ui.view

import androidx.compose.runtime.Composable

@Composable
actual fun ContentAccount(
    label: String?,
    goToBackScreen: () -> Unit,
    extra: @Composable (Boolean) -> Unit,
    content: @Composable () -> Unit
) {
}
