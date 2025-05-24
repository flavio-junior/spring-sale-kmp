package br.com.conding.tv.components.ui

import androidx.compose.runtime.Composable
import br.com.conding.tv.networking.resources.AlternativesRoutes
import br.com.conding.tv.networking.resources.ErrorType
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import br.com.conding.tv.networking.resources.StatusCode.NUMBER_403
import br.com.conding.tv.networking.resources.selectAlternativeRoute
import br.com.conding.tv.resources.WARNINGS.UNAUTHORIZED_MESSAGE
import br.com.conding.tv.theme.NumbersUtils.NUMBER_ZERO

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
