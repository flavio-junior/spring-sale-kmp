package br.com.conding.tv

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import br.com.conding.tv.di.commonModule
import br.com.conding.tv.features.account.di.accountModule
import br.com.conding.tv.features.category.di.categoryModule
import br.com.conding.tv.features.product.di.productModule
import br.com.conding.tv.features.settings.di.settingsModule
import br.com.conding.tv.features.splash.DesktopSplashScreen
import br.com.conding.tv.navigation.AppDestinations
import br.com.conding.tv.navigation.Navigation
import br.com.conding.tv.networking.di.networkModule
import br.com.conding.tv.resources.GenericsStrings.PROJECT_NAME
import br.com.conding.tv.theme.Themes
import br.com.conding.tv.utils.DesktopUtils.DELAY
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import springsale.composeapp.generated.resources.Res
import springsale.composeapp.generated.resources.campaign

internal var destination: AppDestinations = AppDestinations.SplashScreen

fun main() = application {
    if (GlobalContext.getOrNull() == null) {
        startKoin {
            modules(
                modules = listOf(
                    commonModule,
                    accountModule,
                    categoryModule,
                    networkModule,
                    productModule,
                    settingsModule
                )
            )
        }
    }
    var showSplashScreen: Boolean by remember { mutableStateOf(value = true) }
    LaunchedEffect(Unit) {
        delay(timeMillis = DELAY)
        showSplashScreen = false
    }
    if (showSplashScreen) {
        Window(
            onCloseRequest = ::exitApplication,
            icon = painterResource(resource = Res.drawable.campaign),
            undecorated = true,
            transparent = true,
            state = rememberWindowState(
                width = Themes.size.spaceSize700,
                height = Themes.size.spaceSize400,
                position = WindowPosition(Alignment.Center)
            ),
            resizable = false
        ) {
            DesktopSplashScreen(
                goToSignInScreen = {
                    destination = AppDestinations.SignIn
                },
                goToHomeScreen = {
                    destination = AppDestinations.Home
                }
            )
        }
    } else {
        Window(
            onCloseRequest = ::exitApplication,
            icon = painterResource(resource = Res.drawable.campaign),
            title = PROJECT_NAME,
            state = rememberWindowState(placement = WindowPlacement.Maximized)
        ) {
            Navigation(startDestination = destination)
        }
    }
}
