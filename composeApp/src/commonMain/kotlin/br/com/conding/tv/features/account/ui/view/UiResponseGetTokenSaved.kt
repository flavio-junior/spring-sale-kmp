package br.com.conding.tv.features.account.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.conding.tv.components.ui.UiResponse
import br.com.conding.tv.components.ui.UiState
import br.com.conding.tv.features.account.data.vo.TokenResponseVO
import br.com.conding.tv.features.account.ui.viewmodel.AccountViewModel
import br.com.conding.tv.resources.isNotBlankAndEmpty
import br.com.conding.tv.resources.isTokenExpired

@Composable
internal fun UiResponseGetTokenSaved(
    viewModel: AccountViewModel,
    goToSignInScreen: () -> Unit = {},
    goToHomeScreen: () -> Unit = {}
) {
    val uiState: UiState<TokenResponseVO> by viewModel.getTokenSaved.collectAsStateWithLifecycle()
    UiResponse(
        state = uiState,
        onLoading = {},
        onError = {},
        onSuccess = {
            if (it.accessToken.isNotBlankAndEmpty()) {
                if (it.accessToken.isNotBlankAndEmpty()) {
                    if (isTokenExpired(expirationDate = it.expiration)) {
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
