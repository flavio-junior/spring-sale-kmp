package br.com.conding.tv.features.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import br.com.conding.tv.components.ui.ObserveNetworkStateHandler
import br.com.conding.tv.features.account.ui.viewmodel.AccountViewModel
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import br.com.conding.tv.networking.resources.reloadViewModels

@Composable
fun ObserveNetworkStateHandlerLogoutApp(
    viewModel: AccountViewModel,
    onSuccessful: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.cleanToken }
    ObserveNetworkStateHandler(
        state = state,
        goToAlternativeRoutes = {},
        onSuccess = {
            reloadViewModels()
            onSuccessful()
        }
    )
}
