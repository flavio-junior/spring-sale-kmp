package br.com.conding.tv.features.account.ui.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.conding.tv.components.model.DefinitionsScreen
import br.com.conding.tv.components.ui.ContentScreen
import br.com.conding.tv.components.ui.LoadingButton
import br.com.conding.tv.components.ui.UiResponse
import br.com.conding.tv.components.ui.SimpleText
import br.com.conding.tv.components.ui.Title
import br.com.conding.tv.components.ui.UiState
import br.com.conding.tv.features.account.ui.viewmodel.AccountViewModel
import br.com.conding.tv.navigation.AppDestinations
import br.com.conding.tv.networking.resources.AlternativesRoutes
import br.com.conding.tv.resources.GenericsStrings.CREATE_ONE_ACCOUNT
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT
import br.com.conding.tv.resources.GenericsStrings.OR
import br.com.conding.tv.resources.GenericsStrings.RECOVER_MY_ACCOUNT
import org.koin.mp.KoinPlatform.getKoin

@Composable
internal fun CheckRecoverTokenToConfirmEmailScreen(
    checkRecoverToken: AppDestinations.CheckRecoverToken? = null,
    goToSignInScreen: () -> Unit = {},
    goToCreateNewPasswordScreen: (AppDestinations.RecoverToken) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    ContentScreen(
        definitionsScreen = DefinitionsScreen(
            label = RECOVER_MY_ACCOUNT
        ),
        goToBackScreen = goToSignInScreen,
        extra = {
            if (it) Title(label = RECOVER_MY_ACCOUNT)
        },
        content = {
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
            UiResponseCheckRecoverTokenToConfirmEmailScreen(
                viewModel = viewModel,
                onError = {
                    observer = it
                },
                goToCreateNewPasswordScreen = {
                    goToCreateNewPasswordScreen(
                        AppDestinations.RecoverToken(email = checkRecoverToken?.email ?: EMPTY_TEXT)
                    )
                },
                goToAlternativeRoutes = goToAlternativeRoutes
            )
            CheckCodeToConfirmEmail(
                onClick = { checkCode(code) },
                isEnabled = observer.second
            )
            SimpleText(
                label = OR,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            LoadingButton(
                label = CREATE_ONE_ACCOUNT,
                onClick = goToSignInScreen
            )
        }
    )
}

@Composable
private fun UiResponseCheckRecoverTokenToConfirmEmailScreen(
    viewModel: AccountViewModel,
    onError: (Triple<Boolean, Boolean, String?>) -> Unit = {},
    goToCreateNewPasswordScreen: () -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val uiState: UiState<Unit> by viewModel.checkRecoverPassword.collectAsStateWithLifecycle()
    UiResponse(
        state = uiState,
        onError = onError,
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            goToCreateNewPasswordScreen()
        }
    )
}
