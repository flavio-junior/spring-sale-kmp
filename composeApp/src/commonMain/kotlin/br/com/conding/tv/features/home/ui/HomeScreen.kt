package br.com.conding.tv.features.home.ui

import androidx.compose.runtime.Composable

@Composable
expect fun HomeScreen(
    goToNextScreen: (String) -> Unit = {},
    goToLoginScreen: () -> Unit = {}
)
