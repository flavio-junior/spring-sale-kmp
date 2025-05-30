package br.com.conding.tv.components.ui

import androidx.compose.runtime.Composable

@Composable
actual fun ContentScreen(
    label: String?,
    goToBackScreen: () -> Unit,
    extra: @Composable (Boolean) -> Unit,
    content: @Composable () -> Unit
) {

}
