package br.com.conding.tv.features.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.conding.tv.components.ui.UiResponse
import br.com.conding.tv.components.ui.UiState
import br.com.conding.tv.features.account.ui.viewmodel.AccountViewModel
import br.com.conding.tv.networking.resources.reloadViewModels

@Composable
internal fun UiResponseLogoutApp(
    viewModel: AccountViewModel,
    onSuccessful: () -> Unit = {}
) {
    val uiState: UiState<Unit> by viewModel.cleanToken.collectAsStateWithLifecycle()
    UiResponse(
        state = uiState,
        goToAlternativeRoutes = {},
        onSuccess = {
            reloadViewModels()
            onSuccessful()
        }
    )
}
