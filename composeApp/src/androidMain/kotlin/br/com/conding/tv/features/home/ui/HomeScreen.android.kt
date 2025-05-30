package br.com.conding.tv.features.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import br.com.conding.tv.components.ui.BottomNavigation
import br.com.conding.tv.components.ui.Header
import br.com.conding.tv.navigation.AppDestinations
import br.com.conding.tv.theme.Themes

@Composable
actual fun HomeScreen(
    goToNextScreen: (AppDestinations) -> Unit,
    goToLoginScreen: () -> Unit
) {
    var stateOfVisibility by remember { mutableStateOf(value = true) }
    Scaffold(
        topBar = {
            if (stateOfVisibility) {
                Header(label = "Home", textAlign = TextAlign.Center)
            }
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .background(color = Themes.colors.background)
                    .padding(all = Themes.size.spaceSize8)
            ) {
                BottomNavigation()
            }
        }
    ) { innerPadding ->
    }
}
