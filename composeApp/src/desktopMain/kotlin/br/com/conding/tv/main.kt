package br.com.conding.tv

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import br.com.conding.tv.di.desktopModule
import br.com.conding.tv.features.account.di.accountModule
import br.com.conding.tv.features.category.di.categoryModule
import br.com.conding.tv.navigation.Navigation
import br.com.conding.tv.networking.di.networkModule
import br.com.conding.tv.resources.GenericsStrings.PROJECT_NAME
import org.jetbrains.compose.resources.painterResource
import org.koin.core.context.startKoin
import springsale.composeapp.generated.resources.Res
import springsale.composeapp.generated.resources.campaign

fun main() = application {
    startKoin {
        modules(
            modules = listOf(
                accountModule,
                categoryModule,
                desktopModule,
                networkModule
            )
        )
    }
    Window(
        onCloseRequest = ::exitApplication,
        icon = painterResource(resource = Res.drawable.campaign),
        title = PROJECT_NAME,
        state = rememberWindowState(placement = WindowPlacement.Maximized)
    ) {
        Navigation()
    }
}