package br.com.conding.tv.components.ui

import androidx.compose.runtime.Composable
import br.com.conding.tv.networking.resources.AlternativesRoutes
import br.com.conding.tv.networking.resources.ErrorType
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import br.com.conding.tv.networking.resources.StatusCode.NUMBER_403
import br.com.conding.tv.networking.resources.selectAlternativeRoute
import br.com.conding.tv.resources.Warnings.UNAUTHORIZED_MESSAGE
import br.com.conding.tv.theme.NumbersUtils.NUMBER_ZERO

@Composable
fun <T> ObserveNetworkStateHandler2(
    state: UiState<T>,
    onLoading: @Composable () -> Unit = {},
    onError: @Composable (String?) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccess: @Composable (T) -> Unit = {}
) {
    when (state) {
        is UiState.Init -> Unit

        is UiState.Loading -> onLoading()

        is UiState.Error -> {
            when (state.error.type) {
                ErrorType.CLIENT -> {
                    if (state.error.code == NUMBER_403 && state.error.message == UNAUTHORIZED_MESSAGE) {
                        goToAlternativeRoutes(selectAlternativeRoute(code = state.error.code))
                    } else {
                        onError(state.error.message)
                    }
                }

                ErrorType.INTERNAL, ErrorType.EXTERNAL, ErrorType.SERVER -> {
                    goToAlternativeRoutes(
                        selectAlternativeRoute(
                            code = state.error.code ?: NUMBER_ZERO
                        )
                    )
                }
            }
        }

        is UiState.OnSuccess -> onSuccess(state.response)
    }
}


@Composable
fun <R : ObserveNetworkStateHandler<*>> ObserveNetworkStateHandler(
    state: R,
    onLoading: @Composable () -> Unit = {},
    onError: @Composable (String?) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {},
    onSuccess: @Composable (R) -> Unit = {}
) {
    when (state) {
        is ObserveNetworkStateHandler.Loading<*> -> onLoading()

        is ObserveNetworkStateHandler.Error<*> -> {
            when (state.exception.type) {
                ErrorType.CLIENT -> {
                    if (state.exception.code == NUMBER_403 && state.exception.message == UNAUTHORIZED_MESSAGE) {
                        goToAlternativeRoutes(selectAlternativeRoute(code = state.exception.code))
                    } else {
                        onError(state.exception.message)
                    }
                }

                ErrorType.INTERNAL, ErrorType.EXTERNAL, ErrorType.SERVER -> {
                    goToAlternativeRoutes(
                        selectAlternativeRoute(
                            code = state.exception.code ?: NUMBER_ZERO
                        )
                    )
                }
            }
        }

        is ObserveNetworkStateHandler.Success<*> -> onSuccess(state)
    }
}
