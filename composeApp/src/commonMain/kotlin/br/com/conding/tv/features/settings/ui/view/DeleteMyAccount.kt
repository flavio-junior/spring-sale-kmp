package br.com.conding.tv.features.settings.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.conding.tv.components.ui.IsErrorMessage
import br.com.conding.tv.components.ui.LoadingButton
import br.com.conding.tv.features.account.ui.viewmodel.AccountViewModel
import br.com.conding.tv.features.home.ui.UiResponseLogoutApp
import br.com.conding.tv.features.settings.ui.viewmodel.SettingViewModel
import br.com.conding.tv.networking.resources.HttpError
import br.com.conding.tv.resources.GenericsStrings
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT
import br.com.conding.tv.theme.Themes
import org.koin.mp.KoinPlatform.getKoin

@Composable
internal fun DeleteMyAccount(
    modifier: Modifier = Modifier,
    goToAlternativeRoutes: (HttpError) -> Unit = {},
    onSuccess: () -> Unit = {}
) {
    val viewModel: SettingViewModel = getKoin().get()
    var observer: Triple<Boolean, Boolean, String?> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    IsErrorMessage(
        isError = observer.second,
        message = observer.third
    )
    LoadingButton(
        label = GenericsStrings.DELETE_MY_ACCOUNT,
        background = Themes.colors.error,
        isEnabled = observer.first,
        onClick = {
            observer = Triple(first = true, second = false, third = EMPTY_TEXT)
            viewModel.deleteMyAccount()
        },
        modifier = modifier
    )
    UiResponseDeleteMyAccount(
        viewModel = viewModel,
        onError = {
            observer = it
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            val accountViewModel: AccountViewModel = getKoin().get()
            UiResponseLogoutApp(
                viewModel = accountViewModel,
                onSuccessful = onSuccess
            )
        }
    )
}
