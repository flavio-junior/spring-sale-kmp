package br.com.conding.tv.features.account.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import br.com.conding.tv.components.ui.ObserveNetworkStateHandler
import br.com.conding.tv.features.account.viewmodel.AccountViewModel
import br.com.conding.tv.networking.resources.AlternativesRoutes
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT
import org.koin.java.KoinJavaComponent.getKoin

@Composable
internal fun CheckCodeToConfirmEmailScreen(
    emailArg: String? = EMPTY_TEXT,
    goToBackScreen: () -> Unit = {},
    goToSignUpScreen: (email: String) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    ContentAccount(
        content = {
            BodyCheckCodeToConfirmEmailScreen(
                emailArg = emailArg,
                goToBackScreen = goToBackScreen,
                goToSignUpScreen = goToSignUpScreen,
                goToAlternativeRoutes = goToAlternativeRoutes
            )
        }
    )
}

@Composable
private fun BodyCheckCodeToConfirmEmailScreen(
    emailArg: String? = EMPTY_TEXT,
    goToBackScreen: () -> Unit = {},
    goToSignUpScreen: (email: String) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val viewModel: AccountViewModel = getKoin().get()
    var code: String by remember { mutableStateOf(value = EMPTY_TEXT) }
    var state: Triple<Boolean, Boolean, String?> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    val checkCode = { codeArg: String ->
        checkCodeToConfirmEmail(
            data = Pair(first = codeArg, second = viewModel),
            route = TypeScreen.SIGN_UP,
            onError = {
                state = it
            }
        )
    }
    GetCodeToConfirmEmail(
        code = code,
        onError = Pair(state.first, state.third),
        onValueChange = { code = it },
        checkCode = { code = it }
    )
    ObserverStateCheckCodeToConfirmEmail(
        viewModel = viewModel,
        onError = {
            state = it
        },
        goToSignUpScreen = { goToSignUpScreen(emailArg.orEmpty()) },
        goToAlternativeRoutes = goToAlternativeRoutes
    )
    PasteCodeToConfirmEmail(
        onClick = {
        }
    )
    CheckCodeToConfirmEmail(
        onClick = { checkCode(code) },
        isEnabled = state.second
    )
}

@Composable
private fun ObserverStateCheckCodeToConfirmEmail(
    viewModel: AccountViewModel,
    onError: (Triple<Boolean, Boolean, String?>) -> Unit = {},
    goToSignUpScreen: () -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.checkCodeAlreadyExists }
    ObserveNetworkStateHandler(
        state = state,
        onError = {
            onError(Triple(first = true, second = false, third = it))
        },
        goToAlternativeRoutes = {
            goToAlternativeRoutes(it)
        },
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            goToSignUpScreen()
        }
    )
}
