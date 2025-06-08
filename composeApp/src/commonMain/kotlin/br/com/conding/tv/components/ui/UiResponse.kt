package br.com.conding.tv.components.ui

import androidx.compose.runtime.Composable
import br.com.conding.tv.networking.resources.AlternativesRoutes
import br.com.conding.tv.networking.resources.ErrorType
import br.com.conding.tv.networking.resources.StatusCode.NUMBER_403
import br.com.conding.tv.networking.resources.selectAlternativeRoute
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT
import br.com.conding.tv.resources.Warnings.UNAUTHORIZED_MESSAGE
import br.com.conding.tv.theme.NumbersUtils.NUMBER_ZERO

@Composable
fun <T> UiResponse(
    state: UiState<T>,
    onLoading: @Composable () -> Unit = {},
    onError: (Triple<Boolean, Boolean, String?>) -> Unit = {},
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
                        onError(Triple(first = false, second = true, third = state.error.message))
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

        is UiState.OnSuccess -> {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            onSuccess(state.response)
        }
    }
}
