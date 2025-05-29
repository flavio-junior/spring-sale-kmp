package br.com.conding.tv.features.account.ui.view

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
import br.com.conding.tv.components.ui.Title
import br.com.conding.tv.features.account.data.dto.PasswordRequestDTO
import br.com.conding.tv.features.account.data.dto.TokenResponseDTO
import br.com.conding.tv.features.account.ui.viewmodel.AccountViewModel
import br.com.conding.tv.navigation.RecoverToken
import br.com.conding.tv.networking.resources.AlternativesRoutes
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import br.com.conding.tv.resources.GenericsStrings.CONFIRM_PASSWORD
import br.com.conding.tv.resources.GenericsStrings.CREATE_NEW_PASSWORD
import br.com.conding.tv.resources.GenericsStrings.EMAIL
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT
import br.com.conding.tv.resources.GenericsStrings.ENTER_YOUR_ACCOUNT
import br.com.conding.tv.resources.GenericsStrings.INVALID_PASSWORD
import br.com.conding.tv.resources.GenericsStrings.NOT_BLANK_OR_EMPTY
import br.com.conding.tv.resources.GenericsStrings.OR
import br.com.conding.tv.resources.GenericsStrings.PASSWORD
import br.com.conding.tv.resources.GenericsStrings.PASSWORD_ERROR_SIZE
import br.com.conding.tv.resources.GenericsStrings.RECOVER_MY_ACCOUNT
import br.com.conding.tv.resources.IconName
import br.com.conding.tv.resources.Settings.CHECK_SIZE_PASSWORD
import br.com.conding.tv.resources.Warnings.EXPIRED_CODE
import br.com.conding.tv.resources.isBlankAndEmpty
import org.koin.mp.KoinPlatform.getKoin

@Composable
internal fun ResetPasswordScreen(
    recoverToken: RecoverToken? = null,
    goToSignInScreen: () -> Unit = {},
    goToHomeScreen: () -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    ContentAccount(
        label = RECOVER_MY_ACCOUNT,
        goToBackScreen = goToSignInScreen,
        extra = {
            if (it) Title(title = RECOVER_MY_ACCOUNT)
        },
        content = {
            val viewModel: AccountViewModel = getKoin().get()
            var password: String by remember { mutableStateOf(value = EMPTY_TEXT) }
            var confirmPassword: String by remember { mutableStateOf(value = EMPTY_TEXT) }
            var observer: Triple<Boolean, Boolean, String?> by remember {
                mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
            }
            val resetPassword = { passwordArg: String, confirmPasswordArg: String ->
                checkDataResetPassword(
                    resources = Pair(recoverToken?.email ?: EMPTY_TEXT, viewModel),
                    data = Pair(
                        first = passwordArg,
                        second = confirmPasswordArg,
                    ),
                    onError = { observer = it }
                )
            }
            GetDataResetPassword(
                email = recoverToken?.email ?: EMPTY_TEXT,
                password = Pair(first = password, second = confirmPassword),
                onError = Pair(observer.first, observer.third),
                resetPassword = { resetPassword(it.first, it.second) },
                onValueChange = {
                    password = it.first
                    confirmPassword = it.second
                }
            )
            ObserveNetworkStateHandlerResetPassword(
                viewModel = viewModel,
                goToSignInScreen = goToSignInScreen,
                onError = {
                    observer = it
                },
                goToHomeScreen = goToHomeScreen,
                goToAlternativeRoutes = goToAlternativeRoutes
            )
            LoadingButton(
                onClick = {
                    resetPassword(password, confirmPassword)
                },
                isEnabled = observer.second,
                label = CREATE_NEW_PASSWORD
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
private fun GetDataResetPassword(
    email: String,
    password: Pair<String, String>,
    onError: Pair<Boolean, String?>,
    resetPassword: (Pair<String, String>) -> Unit = {},
    onValueChange: (Pair<String, String>) -> Unit = {}
) {
    var passwordMutable: String by remember { mutableStateOf(value = password.first) }
    var confirmPasswordMutable: String by remember { mutableStateOf(value = password.second) }
    TextField(
        enabled = false,
        label = EMAIL,
        value = email,
        isError = onError.first,
        iconName = IconName.MAIL,
        onValueChange = { }
    )
    TextPassword(
        label = PASSWORD,
        value = passwordMutable,
        imeAction = ImeAction.Next,
        isError = onError.first,
        onValueChange = { passwordMutable = it }
    )
    TextPassword(
        label = CONFIRM_PASSWORD,
        value = confirmPasswordMutable,
        imeAction = ImeAction.Go,
        isError = onError.first,
        message = onError.second ?: EMPTY_TEXT,
        onValueChange = { confirmPasswordMutable = it },
        onGo = {
            resetPassword(Pair(passwordMutable, confirmPasswordMutable))
        }
    )
    onValueChange(Pair(passwordMutable, confirmPasswordMutable))
}

private fun checkDataResetPassword(
    resources: Pair<String, AccountViewModel>,
    data: Pair<String, String>,
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {}
) {
    if (data.first.isBlankAndEmpty() && data.second.isBlankAndEmpty()) {
        onError(Triple(first = true, second = false, third = NOT_BLANK_OR_EMPTY))
    } else if (data.first != data.second) {
        onError(Triple(first = true, second = false, third = INVALID_PASSWORD))
    } else if (data.first.length < CHECK_SIZE_PASSWORD) {
        onError(Triple(first = true, second = false, third = PASSWORD_ERROR_SIZE))
    } else {
        onError(Triple(first = false, second = true, third = EMPTY_TEXT))
        resources.second.createNewPassword(
            passwordRequestDTO = PasswordRequestDTO(
                email = resources.first,
                password = data.first
            )
        )
    }
}

@Composable
private fun ObserveNetworkStateHandlerResetPassword(
    viewModel: AccountViewModel,
    goToSignInScreen: () -> Unit = {},
    goToHomeScreen: () -> Unit = {},
    onError: (Triple<Boolean, Boolean, String?>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val resetState: ObserveNetworkStateHandler<Unit> by remember { viewModel.createNewPassword }
    ObserveNetworkStateHandler(
        state = resetState,
        onError = {
            onError(Triple(first = true, second = false, third = it))
            if (it == EXPIRED_CODE) {
                goToSignInScreen()
            }
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
        }
    )
    val state: ObserveNetworkStateHandler<TokenResponseDTO> by remember { viewModel.signIn }
    ObserveNetworkStateHandler(
        state = state,
        onError = {
            onError(Triple(first = true, second = false, third = it))
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            goToHomeScreen()
        }
    )
}
