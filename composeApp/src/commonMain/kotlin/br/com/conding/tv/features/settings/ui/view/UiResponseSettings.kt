package br.com.conding.tv.features.settings.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.conding.tv.components.ui.UiResponse
import br.com.conding.tv.components.ui.UiState
import br.com.conding.tv.features.settings.data.vo.UserResponseVO
import br.com.conding.tv.features.settings.ui.viewmodel.SettingViewModel
import br.com.conding.tv.networking.resources.HttpError

@Composable
internal fun UiResponseGetUserAuthenticated(
    viewModel: SettingViewModel,
    onError: (Triple<Boolean, Boolean, String?>) -> Unit = {},
    goToAlternativeRoutes: (HttpError) -> Unit = {},
    onSuccess: @Composable (UserResponseVO) -> Unit = {}
) {
    val uiState by viewModel.getUserAuthenticated.collectAsStateWithLifecycle()
    UiResponse(
        state = uiState,
        onError = onError,
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = onSuccess
    )
}

@Composable
internal fun UiResponseChangeInfoUser(
    viewModel: SettingViewModel,
    onError: (Triple<Boolean, Boolean, String?>) -> Unit = {},
    goToAlternativeRoutes: (HttpError) -> Unit = {}
) {
    val uiState: UiState<Unit> by viewModel.changeInfoUser.collectAsStateWithLifecycle()
    UiResponse(
        state = uiState,
        onError = onError,
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            viewModel.getUserAuthenticated()
        }
    )
}

@Composable
internal fun UiResponseDeleteMyAccount(
    viewModel: SettingViewModel,
    onError: (Triple<Boolean, Boolean, String?>) -> Unit = {},
    goToAlternativeRoutes: (HttpError) -> Unit = {},
    onSuccess: @Composable () -> Unit = {}
) {
    val uiState: UiState<Unit> by viewModel.deleteMyAccount.collectAsStateWithLifecycle()
    UiResponse(
        state = uiState,
        onError = onError,
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            onSuccess()
        }
    )
}
