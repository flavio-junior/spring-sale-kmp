package br.com.conding.tv.features.account.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import br.com.conding.tv.components.ui.ObserveNetworkStateHandler
import br.com.conding.tv.features.account.data.vo.TokenResponseVO
import br.com.conding.tv.features.account.ui.viewmodel.AccountViewModel
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import br.com.conding.tv.resources.isNotBlankAndEmpty
import br.com.conding.tv.resources.isTokenExpired

@Composable
internal fun ObserveNetworkStateHandlerGetTokenSaved(
    viewModel: AccountViewModel,
    goToSignInScreen: () -> Unit = {},
    goToHomeScreen: () -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<TokenResponseVO> by remember { viewModel.getTokenSaved }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {},
        onError = {},
        onSuccess = {
            if (state.result != null && state.result?.accessToken?.isNotBlankAndEmpty() == true) {
                val tokenResponseVO = state.result
                if (tokenResponseVO?.accessToken?.isNotBlankAndEmpty() == true) {
                    if (isTokenExpired(expirationDate = tokenResponseVO.expiration)) {
                        viewModel.cleanToken()
                        goToSignInScreen()
                    } else {
                        goToHomeScreen()
                    }
                }
            }
            goToSignInScreen()
        }
    )
}
