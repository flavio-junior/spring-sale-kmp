package br.com.conding.tv.features.settings.ui.view

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
import br.com.conding.tv.features.account.ui.view.UiResponseCleanToken
import br.com.conding.tv.navigation.AppDestinations
import br.com.conding.tv.networking.resources.HttpError
import br.com.conding.tv.resources.GenericStrings
import br.com.conding.tv.resources.GenericStrings.EXIT
import br.com.conding.tv.resources.IconName
import br.com.conding.tv.resources.WeightSize
import br.com.conding.tv.resources.onClickable
import br.com.conding.tv.resources.versionProduct
import br.com.conding.tv.theme.Themes

@Composable
internal expect fun SettingsScreen(
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (AppDestinations) -> Unit = {},
    goToAlternativeRoutes: (HttpError) -> Unit = {}
)

@Composable
internal fun MobileSettingsScreen(
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (AppDestinations) -> Unit = {},
    goToAlternativeRoutes: (HttpError) -> Unit = {}
) {
    var openDialog: Boolean by remember { mutableStateOf(value = false) }
    var cleanToken: Boolean by remember { mutableStateOf(value = false) }
    ContentScreen(
        definitionsScreen = DefinitionsScreen(
            alignment = Alignment.Top
        ),
        content = {
            Title(
                label = GenericStrings.SETTINGS,
                backgroundTransparent = true,
                modifier = Modifier.fillMaxWidth()
            )
            ContentHorizontal(
                spaceBy = Themes.size.spaceSize18,
                content = {
                    IconDefault(iconName = IconName.USER)
                    SubTitle(
                        label = GenericStrings.DATA_USER,
                        backgroundTransparent = true,
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
                        backgroundTransparent = true,
                        modifier = Modifier
                            .onClickable {
                                openDialog = true
                            }
                            .weight(weight = WeightSize.WEIGHT_SIZE)
                    )
                }
            )
            ContentHorizontal(
                spaceBy = Themes.size.spaceSize18,
                content = {
                    IconDefault(iconName = IconName.COMMIT)
                    SubTitle(
                        label = "${GenericStrings.VERSION} ${versionProduct()}",
                        backgroundTransparent = true,
                        modifier = Modifier.weight(weight = WeightSize.WEIGHT_SIZE)
                    )
                }
            )
        }
    )
    if (openDialog) {
        ExitAppBottomSheet(
            cleanToken = {
                openDialog = false
                cleanToken = true
            },
            onDismiss = {
                openDialog = false
            }
        )
    }
    if (cleanToken) {
        UiResponseCleanToken(
            goToAlternativeRoutes = goToAlternativeRoutes,
            onSuccess = {
                goToNextScreen(AppDestinations.SignIn)
            }
        )
    }
}
