package br.com.conding.tv.features.account.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import br.com.conding.tv.components.ui.LoadingButton
import br.com.conding.tv.components.ui.ObserveNetworkStateHandler
import br.com.conding.tv.components.ui.SimpleText
import br.com.conding.tv.components.ui.TextField
import br.com.conding.tv.components.ui.TextPassword
import br.com.conding.tv.features.account.data.dto.SignUpRequestDTO
import br.com.conding.tv.features.account.data.dto.TokenResponseDTO
import br.com.conding.tv.features.account.viewmodel.AccountViewModel
import br.com.conding.tv.navigation.SignUp
import br.com.conding.tv.networking.resources.AlternativesRoutes
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import br.com.conding.tv.resources.GenericsStrings.CONFIRM_PASSWORD
import br.com.conding.tv.resources.GenericsStrings.CREATE_MY_ACCOUNT
import br.com.conding.tv.resources.GenericsStrings.EMAIL
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT
import br.com.conding.tv.resources.GenericsStrings.ENTER_YOUR_ACCOUNT
import br.com.conding.tv.resources.GenericsStrings.INVALID_PASSWORD
import br.com.conding.tv.resources.GenericsStrings.NOT_BLANK_OR_EMPTY
import br.com.conding.tv.resources.GenericsStrings.OR
import br.com.conding.tv.resources.GenericsStrings.PASSWORD
import br.com.conding.tv.resources.GenericsStrings.PASSWORD_ERROR_SIZE
import br.com.conding.tv.resources.Settings
import br.com.conding.tv.resources.isBlankAndEmpty
import org.koin.java.KoinJavaComponent.getKoin
import springsale.composeapp.generated.resources.Res
import springsale.composeapp.generated.resources.mail

@Composable
internal fun SignUpScreen(
    signUp: SignUp? = null,
    goToHomeScreen: () -> Unit = {},
    goToSignInScreen: () -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    ContentAccount(
        content = {
            val viewModel: AccountViewModel = getKoin().get()
            var password: String by remember { mutableStateOf(value = EMPTY_TEXT) }
            var confirmPassword: String by remember { mutableStateOf(value = EMPTY_TEXT) }
            var observer: Triple<Boolean, Boolean, String?> by remember {
                mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
            }
            val signUpArg = { passwordArg: String, confirmPasswordArg: String ->
                checkDataSignUp(
                    resources = Pair(signUp?.email ?: EMPTY_TEXT, viewModel),
                    data = Pair(first = passwordArg, second = confirmPasswordArg),
                    onError = { observer = it }
                )
            }
            GetDataPasswordSignUp(
                email = signUp?.email ?: EMPTY_TEXT,
                password = Pair(first = password, second = confirmPassword),
                onError = observer,
                confirmPassword = { signUpArg(it.first, it.second) },
                onValueChange = {
                    password = it.first
                    confirmPassword = it.second
                }
            )
            ObserveNetworkStateHandlerSignUp(
                viewModel = viewModel,
                onError = {
                    observer = it
                },
                goToHomeScreen = goToHomeScreen,
                goToAlternativeRoutes = goToAlternativeRoutes
            )
            LoadingButton(
                onClick = {
                    signUpArg(password, confirmPassword)
                },
                isEnabled = observer.first,
                label = CREATE_MY_ACCOUNT
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
private fun GetDataPasswordSignUp(
    email: String,
    password: Pair<String, String>,
    onError: Triple<Boolean, Boolean, String?>,
    confirmPassword: (Pair<String, String>) -> Unit = {},
    onValueChange: (Pair<String, String>) -> Unit = {}
) {
    var passwordMutable: String by remember { mutableStateOf(value = password.first) }
    var confirmPasswordMutable: String by remember { mutableStateOf(value = password.second) }
    TextField(
        enabled = false,
        label = EMAIL,
        value = email,
        icon = Res.drawable.mail,
        onValueChange = { }
    )
    TextPassword(
        label = PASSWORD,
        value = passwordMutable,
        imeAction = ImeAction.Next,
        isError = onError.second,
        onValueChange = { passwordMutable = it }
    )
    TextPassword(
        label = CONFIRM_PASSWORD,
        value = confirmPasswordMutable,
        imeAction = ImeAction.Go,
        isError = onError.second,
        message = onError.third ?: EMPTY_TEXT,
        onValueChange = { confirmPasswordMutable = it },
        onGo = {
            confirmPassword(Pair(passwordMutable, confirmPasswordMutable))
        }
    )
    onValueChange(Pair(first = passwordMutable, second = confirmPasswordMutable))
}

private fun checkDataSignUp(
    resources: Pair<String, AccountViewModel>,
    data: Pair<String, String>,
    onError: (Triple<Boolean, Boolean, String?>) -> Unit = {}
) {
    if (data.first.isBlankAndEmpty() && data.second.isBlankAndEmpty()) {
        onError(Triple(first = false, second = true, third = NOT_BLANK_OR_EMPTY))
    } else if (data.first != data.second) {
        onError(Triple(first = false, second = true, third = INVALID_PASSWORD))
    } else if (data.first.length < Settings.CHECK_SIZE_PASSWORD) {
        onError(Triple(first = false, second = true, third = PASSWORD_ERROR_SIZE))
    } else {
        onError(Triple(first = true, second = false, third = EMPTY_TEXT))
        resources.second.signUp(
            signUpRequestDTO = SignUpRequestDTO(email = resources.first, password = data.first)
        )
    }
}

@Composable
private fun ObserveNetworkStateHandlerSignUp(
    viewModel: AccountViewModel,
    onError: (Triple<Boolean, Boolean, String?>) -> Unit = {},
    goToHomeScreen: () -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<TokenResponseDTO> by remember { viewModel.signIn }
    ObserveNetworkStateHandler(
        state = state,
        onError = {
            onError(Triple(first = false, second = true, third = it))
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            goToHomeScreen()
        }
    )
}
