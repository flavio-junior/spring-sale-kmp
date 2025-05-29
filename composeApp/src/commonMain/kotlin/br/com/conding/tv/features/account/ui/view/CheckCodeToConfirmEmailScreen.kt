package br.com.conding.tv.features.account.ui.view

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
import br.com.conding.tv.components.ui.Title
import br.com.conding.tv.features.account.ui.viewmodel.AccountViewModel
import br.com.conding.tv.features.account.ui.viewmodel.ResetAccount
import br.com.conding.tv.navigation.CheckCodeToConfirmEmail
import br.com.conding.tv.navigation.SignUp
import br.com.conding.tv.networking.resources.AlternativesRoutes
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import br.com.conding.tv.resources.GenericsStrings.CREATE_MY_ACCOUNT
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT
import br.com.conding.tv.resources.GenericsStrings.ENTER_YOUR_ACCOUNT
import br.com.conding.tv.resources.GenericsStrings.OR
import org.koin.mp.KoinPlatform.getKoin

@Composable
internal fun CheckCodeToConfirmEmailScreen(
    checkCodeToConfirmEmail: CheckCodeToConfirmEmail? = null,
    goToSignUpScreen: (SignUp) -> Unit = {},
    goToSignInScreen: () -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    ContentAccount(
        content = {
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
            Title(title = CREATE_MY_ACCOUNT)
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
                goToSignUpScreen = { goToSignUpScreen(SignUp(email = checkCodeToConfirmEmail?.email.orEmpty())) },
                goToAlternativeRoutes = goToAlternativeRoutes
            )
            CheckCodeToConfirmEmail(
                onClick = { checkCode(code) },
                isEnabled = state.second
            )
            SimpleText(
                text = OR,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            LoadingButton(
                label = ENTER_YOUR_ACCOUNT,
                onClick = goToSignInScreen
            )
        }
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
            viewModel.resetStateSignIn(resetAccount = ResetAccount.CHECK_CODE_ALREADY_EXISTS)
        },
        goToAlternativeRoutes = {
            goToAlternativeRoutes(it)
        },
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            viewModel.resetStateSignIn(resetAccount = ResetAccount.CHECK_CODE_ALREADY_EXISTS)
            goToSignUpScreen()
        }
    )
}
