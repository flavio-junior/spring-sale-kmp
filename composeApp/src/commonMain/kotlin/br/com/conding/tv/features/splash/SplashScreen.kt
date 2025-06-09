package br.com.conding.tv.features.splash

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import br.com.conding.tv.components.ui.ContentScreen
import br.com.conding.tv.components.ui.Title
import br.com.conding.tv.features.account.ui.view.UiResponseGetTokenSaved
import br.com.conding.tv.features.account.ui.viewmodel.AccountViewModel
import br.com.conding.tv.resources.GenericsStrings
import br.com.conding.tv.theme.Themes
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.koin.mp.KoinPlatform.getKoin
import springsale.composeapp.generated.resources.Res
import springsale.composeapp.generated.resources.coding_tv

@Composable
internal expect fun SplashScreen(
    goToSignInScreen: () -> Unit = {},
    goToHomeScreen: () -> Unit = {}
)

@Composable
fun MobileSplashScreen(
    goToSignInScreen: () -> Unit = {},
    goToHomeScreen: () -> Unit = {}
) {
    val viewModel: AccountViewModel = getKoin().get()
    ContentScreen(
        content = {
            LaunchedEffect(key1 = Unit) {
                delay(timeMillis = 5000)
                viewModel.getToken()
            }
            Image(
                painter = painterResource(resource = Res.drawable.coding_tv),
                contentDescription = null
            )
            Title(
                label = GenericsStrings.PROJECT_NAME,
                color = Themes.colors.primary
            )
            UiResponseGetTokenSaved(
                viewModel = viewModel,
                goToSignInScreen = goToSignInScreen,
                goToHomeScreen = goToHomeScreen
            )
        }
    )
}
