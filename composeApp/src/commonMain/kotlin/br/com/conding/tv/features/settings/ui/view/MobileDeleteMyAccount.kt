package br.com.conding.tv.features.settings.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import br.com.conding.tv.components.ui.LoadingButton
import br.com.conding.tv.features.settings.ui.viewmodel.SettingViewModel
import br.com.conding.tv.networking.resources.HttpError
import br.com.conding.tv.resources.GenericStrings
import br.com.conding.tv.theme.Themes
import org.koin.mp.KoinPlatform.getKoin

@Composable
internal fun MobileDeleteMyAccount(
    goToAlternativeRoutes: (HttpError) -> Unit = {},
    onSuccess: () -> Unit = {}
) {
    val viewModel: SettingViewModel = getKoin().get()
    var openDialog: Boolean by remember { mutableStateOf(value = false) }
    LoadingButton(
        label = GenericStrings.DELETE_MY_ACCOUNT,
        onClick = {
            openDialog = true
        },
        background = Themes.colors.error
    )
    if (openDialog) {
        DeleteMyAccountBottomSheet(
            viewModel = viewModel,
            onDismiss = {
                openDialog = false
            }
        )
    }
    UiResponseDeleteMyAccount(
        viewModel = viewModel,
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            openDialog = false
            onSuccess()
        }
    )
}
