package br.com.conding.tv.features.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.conding.tv.features.account.ui.view.ObserveNetworkStateHandlerGetTokenSaved
import br.com.conding.tv.features.account.ui.viewmodel.AccountViewModel
import br.com.conding.tv.theme.Themes
import org.jetbrains.compose.resources.painterResource
import org.koin.mp.KoinPlatform.getKoin
import springsale.composeapp.generated.resources.Res
import springsale.composeapp.generated.resources.coding_tv

@Composable
actual fun SplashScreen(
    goToSignInScreen: () -> Unit,
    goToHomeScreen: () -> Unit
) {
    DesktopSplashScreen(
        goToSignInScreen = goToSignInScreen,
        goToHomeScreen = goToHomeScreen
    )
}

@Composable
internal fun DesktopSplashScreen(
    goToSignInScreen: () -> Unit,
    goToHomeScreen: () -> Unit
) {
    Box(
        modifier = Modifier.background(color = Themes.colors.background)
    ) {
        Image(
            painter = painterResource(resource = Res.drawable.coding_tv),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .align(alignment = Alignment.Center)
        )
    }
    val viewModel: AccountViewModel = getKoin().get()
    LaunchedEffect(Unit) {
        viewModel.getToken()
    }
    ObserveNetworkStateHandlerGetTokenSaved(
        viewModel = viewModel,
        goToSignInScreen = goToSignInScreen,
        goToHomeScreen = goToHomeScreen
    )
}
