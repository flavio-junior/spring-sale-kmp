package br.com.conding.tv.features.account.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import br.com.conding.tv.components.ui.LoadingButton
import br.com.conding.tv.components.ui.ObserveNetworkStateHandler
import br.com.conding.tv.components.ui.SimpleText
import br.com.conding.tv.features.account.viewmodel.AccountViewModel
import br.com.conding.tv.networking.resources.AlternativesRoutes
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import br.com.conding.tv.resources.GenericsStrings.CREATE_ONE_ACCOUNT
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT
import br.com.conding.tv.resources.GenericsStrings.OR
import org.koin.java.KoinJavaComponent.getKoin

@Composable
internal fun CheckRecoverTokenToConfirmEmailScreen(
    emailArg: String? = EMPTY_TEXT,
    goToBackScreen: () -> Unit = {},
    goToCreateNewPasswordScreen: (email: String) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    ContentAccount(
        content = {
            BodyCheckRecoverTokenToConfirmEmailScreen(
                emailArg = emailArg,
                signInScreen = goToBackScreen,
                goToCreateNewPasswordScreen = goToCreateNewPasswordScreen,
                goToAlternativeRoutes = goToAlternativeRoutes
            )
        }
    )
}

@Composable
private fun BodyCheckRecoverTokenToConfirmEmailScreen(
    emailArg: String? = EMPTY_TEXT,
    signInScreen: () -> Unit = {},
    goToCreateNewPasswordScreen: (email: String) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val viewModel: AccountViewModel = getKoin().get()
    var code: String by remember { mutableStateOf(value = EMPTY_TEXT) }
    var observer: Triple<Boolean, Boolean, String?> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    val checkCode = { codeArg: String ->
        checkCodeToConfirmEmail(
            data = Pair(first = codeArg, second = viewModel),
            route = TypeScreen.RECOVER_TOKEN,
            onError = {
                observer = it
            }
        )
    }
    GetCodeToConfirmEmail(
        code = code,
        onError = Pair(observer.first, observer.third),
        onValueChange = { code = it },
        checkCode = { code = it }
    )
    ObserverStateCheckRecoverTokenToConfirmEmail(
        viewModel = viewModel,
        onError = {
            observer = it
        },
        goToCreateNewPasswordScreen = { goToCreateNewPasswordScreen(emailArg.orEmpty()) },
        goToAlternativeRoutes = goToAlternativeRoutes
    )
    PasteCodeToConfirmEmail(
        onClick = {
        }
    )
    CheckCodeToConfirmEmail(
        onClick = { checkCode(code) },
        isEnabled = observer.second
    )
    SimpleText(
        text = OR,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
    LoadingButton(
        label = CREATE_ONE_ACCOUNT,
        onClick = signInScreen
    )
}

@Composable
private fun ObserverStateCheckRecoverTokenToConfirmEmail(
    viewModel: AccountViewModel,
    onError: (Triple<Boolean, Boolean, String?>) -> Unit = {},
    goToCreateNewPasswordScreen: () -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.checkRecoverPassword }
    ObserveNetworkStateHandler(
        state = state,
        onError = {
            onError(Triple(first = true, second = false, third = it))
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            goToCreateNewPasswordScreen()
        }
    )
}
