package br.com.conding.tv.features.account.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.conding.tv.components.ui.UiResponse
import br.com.conding.tv.components.ui.UiState
import br.com.conding.tv.features.account.data.dto.TokenResponseDTO
import br.com.conding.tv.features.account.data.vo.TokenResponseVO
import br.com.conding.tv.features.account.ui.viewmodel.AccountViewModel
import br.com.conding.tv.features.account.ui.viewmodel.ResetAccount
import br.com.conding.tv.networking.resources.HttpError
import br.com.conding.tv.networking.resources.reloadViewModels
import br.com.conding.tv.resources.isNotBlankAndEmpty
import br.com.conding.tv.resources.isTokenExpired
import org.koin.mp.KoinPlatform.getKoin

@Composable
internal fun UiResponseSignIn(
    viewModel: AccountViewModel,
    onError: (Triple<Boolean, Boolean, String?>) -> Unit = {},
    goToAlternativeRoutes: (HttpError) -> Unit = {},
    goToHomeScreen: () -> Unit = {}
) {
    val uiState: UiState<TokenResponseDTO> by viewModel.signIn.collectAsStateWithLifecycle()
    UiResponse(
        state = uiState,
        onError = onError,
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            viewModel.resetStateSignIn(resetAccount = ResetAccount.SIGN_IN)
            goToHomeScreen()
        }
    )
}

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

@Composable
internal fun UiResponseCleanToken(
    goToAlternativeRoutes: (HttpError) -> Unit = {},
    onSuccess: () -> Unit = {}
) {
    val viewModel: AccountViewModel = getKoin().get()
    viewModel.cleanToken()
    val uiState: UiState<Unit> by viewModel.cleanToken.collectAsStateWithLifecycle()
    UiResponse(
        state = uiState,
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            reloadViewModels()
            onSuccess()
        }
    )
}
