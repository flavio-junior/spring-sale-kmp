package br.com.conding.tv.features.settings

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.conding.tv.components.model.DefinitionsScreen
import br.com.conding.tv.components.ui.ContentHorizontal
import br.com.conding.tv.components.ui.ContentScreen
import br.com.conding.tv.components.ui.IconDefault
import br.com.conding.tv.components.ui.SubTitle
import br.com.conding.tv.components.ui.Title
import br.com.conding.tv.features.account.ui.viewmodel.AccountViewModel
import br.com.conding.tv.features.home.ui.ObserveNetworkStateHandlerLogoutApp
import br.com.conding.tv.navigation.AppDestinations
import br.com.conding.tv.networking.resources.AlternativesRoutes
import br.com.conding.tv.resources.GenericsStrings
import br.com.conding.tv.resources.GenericsStrings.EXIT
import br.com.conding.tv.resources.IconName
import br.com.conding.tv.resources.WeightSize
import br.com.conding.tv.resources.onClickable
import br.com.conding.tv.theme.Themes
import org.koin.mp.KoinPlatform.getKoin

@Composable
expect fun SettingsScreen(
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (AppDestinations) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
)

@Composable
fun MobileSettingsScreen(
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (AppDestinations) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val viewModel: AccountViewModel = getKoin().get()
    var logoutApp: Boolean by remember { mutableStateOf(value = false) }
    ContentScreen(
        definitionsScreen = DefinitionsScreen(
            alignment = Alignment.Top
        ),
        content = {
            Title(
                label = GenericsStrings.SETTINGS,
                modifier = Modifier.fillMaxWidth()
            )
            ContentHorizontal(
                spaceBy = Themes.size.spaceSize18,
                content = {
                    IconDefault(iconName = IconName.USER)
                    SubTitle(
                        label = GenericsStrings.DATA_USER,
                        modifier = Modifier
                            .onClickable(
                                onClick = {
                                    goToNextScreen(AppDestinations.EditProfileUser)
                                }
                            )
                            .weight(weight = WeightSize.WEIGHT_SIZE)
                    )
                }
            )
            ContentHorizontal(
                spaceBy = Themes.size.spaceSize18,
                content = {
                    IconDefault(iconName = IconName.LOGOUT)
                    SubTitle(
                        label = EXIT,
                        modifier = Modifier
                            .onClickable {
                                logoutApp = true
                            }
                            .weight(weight = WeightSize.WEIGHT_SIZE)
                    )
                }
            )
        }
    )
    if (logoutApp) {
        viewModel.cleanToken()
        ObserveNetworkStateHandlerLogoutApp(
            viewModel = viewModel,
            onSuccessful = {
                goToNextScreen(AppDestinations.SignIn)
                logoutApp = false
            }
        )
    }
}
