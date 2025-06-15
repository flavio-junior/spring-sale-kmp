package br.com.conding.tv.features.settings.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.conding.tv.components.ui.IsErrorMessage
import br.com.conding.tv.components.ui.LoadingButton
import br.com.conding.tv.features.settings.ui.viewmodel.SettingViewModel
import br.com.conding.tv.features.shared.ShowOverlayPanel
import br.com.conding.tv.networking.resources.HttpError
import br.com.conding.tv.resources.GenericStrings
import br.com.conding.tv.resources.GenericStrings.EMPTY_TEXT
import br.com.conding.tv.theme.Themes
import org.koin.mp.KoinPlatform.getKoin

@Composable
internal fun DesktopDeleteMyAccount(
    modifier: Modifier = Modifier,
    goToAlternativeRoutes: (HttpError) -> Unit = {},
    onSuccess: () -> Unit = {}
) {
    val viewModel: SettingViewModel = getKoin().get()
    var observer: Triple<Boolean, Boolean, String?> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    var showOverlayPanel: Boolean by remember { mutableStateOf(value = false) }
    IsErrorMessage(
        isError = observer.second,
        message = observer.third
    )
    LoadingButton(
        label = GenericStrings.DELETE_MY_ACCOUNT,
        background = Themes.colors.error,
        isEnabled = observer.first,
        onClick = {
            showOverlayPanel = true
        },
        modifier = modifier
    )
    if (showOverlayPanel) {
        ShowOverlayPanel(
            labelStart = GenericStrings.YES,
            labelEnd = GenericStrings.NO,
            description = GenericStrings.WARNING_THE_DELETE_MY_ACCOUNT,
            callNextAction = {
                showOverlayPanel = false
                observer = Triple(first = true, second = false, third = EMPTY_TEXT)
                viewModel.deleteMyAccount()
            },
            cancelAction = {
                showOverlayPanel = false
            },
            onDismissRequest = {
                showOverlayPanel = false
            }
        )
    }
    UiResponseDeleteMyAccount(
        viewModel = viewModel,
        onError = {
            observer = it
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = onSuccess
    )
}
