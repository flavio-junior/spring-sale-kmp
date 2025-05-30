package br.com.conding.tv.features.main.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.conding.tv.navigation.Navigation
import br.com.conding.tv.theme.Theme

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    Surface(modifier = modifier.fillMaxSize()) {
        Theme {
            Navigation()
        }
    }
}