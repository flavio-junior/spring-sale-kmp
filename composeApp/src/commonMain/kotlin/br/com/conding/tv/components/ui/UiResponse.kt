package br.com.conding.tv.components.ui

import androidx.compose.runtime.Composable
import br.com.conding.tv.features.account.ui.view.UiResponseCleanToken
import br.com.conding.tv.networking.resources.ErrorType
import br.com.conding.tv.networking.resources.HttpError
import br.com.conding.tv.networking.resources.StatusCode.NUMBER_401
import br.com.conding.tv.networking.resources.StatusCode.NUMBER_403
import br.com.conding.tv.networking.resources.determineErrorRoute
import br.com.conding.tv.networking.resources.reloadViewModels
import br.com.conding.tv.resources.GenericStrings.EMPTY_TEXT
import br.com.conding.tv.resources.Warnings.UNAUTHORIZED_MESSAGE

@Composable
internal fun <T> UiResponse(
    state: UiState<T>,
    onLoading: @Composable () -> Unit = {},
    onError: (Triple<Boolean, Boolean, String?>) -> Unit = {},
    goToAlternativeRoutes: (HttpError) -> Unit = {},
    onSuccess: @Composable (T) -> Unit = {}
) {
    when (state) {
        is UiState.Init -> Unit

        is UiState.Loading -> onLoading()

        is UiState.Error -> {
            val error = state.error
            when (error.type) {
                ErrorType.CLIENT -> {
                    if (error.code == NUMBER_403 && error.message == UNAUTHORIZED_MESSAGE) {
                        goToAlternativeRoutes(HttpError.ERROR_403)
                    } else if (error.code == NUMBER_401) {
                        UiResponseCleanToken(
                            onSuccess = {
                                goToAlternativeRoutes(HttpError.ERROR_401)
                            }
                        )
                    } else {
                        onError(Triple(first = false, second = true, third = error.message))
                    }
                }

                ErrorType.INTERNAL, ErrorType.EXTERNAL, ErrorType.SERVER -> {
                    reloadViewModels()
                    determineErrorRoute(
                        code = error.code,
                        goToAlternativeRoutes = goToAlternativeRoutes
                    )
                }
            }
        }

        is UiState.OnSuccess -> {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            onSuccess(state.response)
        }
    }
}
