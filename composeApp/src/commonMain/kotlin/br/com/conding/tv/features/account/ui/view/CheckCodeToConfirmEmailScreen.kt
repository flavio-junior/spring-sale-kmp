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
import br.com.conding.tv.features.account.ui.viewmodel.ResetAccount
import br.com.conding.tv.navigation.AppDestinations
import br.com.conding.tv.networking.resources.HttpError
import br.com.conding.tv.resources.GenericStrings.CREATE_MY_ACCOUNT
import br.com.conding.tv.resources.GenericStrings.EMPTY_TEXT
import br.com.conding.tv.resources.GenericStrings.ENTER_YOUR_ACCOUNT
import br.com.conding.tv.resources.GenericStrings.OR
import org.koin.mp.KoinPlatform.getKoin

@Composable
internal fun CheckCodeToConfirmEmailScreen(
    checkCodeToConfirmEmail: AppDestinations.CheckCodeToConfirmEmail? = null,
    goToSignUpScreen: (AppDestinations.SignUp) -> Unit = {},
    goToSignInScreen: () -> Unit = {},
    goToAlternativeRoutes: (HttpError) -> Unit = {}
) {
    ContentScreen(
        definitionsScreen = DefinitionsScreen(
            label = CREATE_MY_ACCOUNT
        ),
        goToBackScreen = goToSignInScreen,
        extra = {
            if (it) Title(label = CREATE_MY_ACCOUNT, backgroundTransparent = true,)
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
                    route = TypeScreen.SIGN_UP,
                    onError = {
                        observer = it
                    }
                )
            }
            GetCodeToConfirmEmail(
                code = code,
                onError = Pair(observer.second, observer.third),
                onValueChange = { code = it },
                checkCode = { code = it }
            )
            UiResponseCheckCodeToConfirmEmailScreen(
                viewModel = viewModel,
                onError = {
                    observer = it
                },
                goToSignUpScreen = {
                    goToSignUpScreen(
                        AppDestinations.SignUp(email = checkCodeToConfirmEmail?.email.orEmpty())
                    )
                },
                goToAlternativeRoutes = goToAlternativeRoutes
            )
            CheckCodeToConfirmEmail(
                onClick = { checkCode(code) },
                isEnabled = observer.first
            )
            SimpleText(
                label = OR,
                backgroundTransparent = true,
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
private fun UiResponseCheckCodeToConfirmEmailScreen(
    viewModel: AccountViewModel,
    onError: (Triple<Boolean, Boolean, String?>) -> Unit = {},
    goToSignUpScreen: () -> Unit = {},
    goToAlternativeRoutes: (HttpError) -> Unit = {}
) {
    val uiState: UiState<Unit> by viewModel.checkCodeAlreadyExists.collectAsStateWithLifecycle()
    UiResponse(
        state = uiState,
        onError = {
            onError(it)
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
