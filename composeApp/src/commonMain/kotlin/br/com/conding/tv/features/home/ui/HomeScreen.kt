package br.com.conding.tv.features.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.conding.tv.components.ui.BottomNavigationBar
import br.com.conding.tv.components.ui.Header
import br.com.conding.tv.navigation.AppDestinations
import br.com.conding.tv.resources.GenericStrings.PROJECT_NAME
import br.com.conding.tv.theme.Themes

@Composable
internal expect fun HomeScreen(
    navGraph: NavHostController,
    goToNextScreen: (AppDestinations) -> Unit = {},
    goToLoginScreen: () -> Unit = {}
)

@Composable
internal fun MobileHomeScreen(
    navGraph: NavHostController,
    goToNextScreen: (AppDestinations) -> Unit = {},
    goToLoginScreen: () -> Unit = {}
) {
    val navController: NavHostController = rememberNavController()
    Scaffold(
        topBar = {
            Header(label = PROJECT_NAME, disableIcon = true)
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .background(color = Themes.colors.background)
                    .padding(all = Themes.size.spaceSize8)
            ) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavigationRail(
            modifier = Modifier.padding(paddingValues = innerPadding),
            navController = navController,
            navGraph = navGraph
        )
    }
}