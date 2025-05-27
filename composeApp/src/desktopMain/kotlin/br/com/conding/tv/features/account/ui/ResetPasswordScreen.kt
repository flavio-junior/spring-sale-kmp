package br.com.conding.tv.features.account.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.ImeAction
import br.com.conding.tv.components.ui.LoadingButton
import br.com.conding.tv.components.ui.ObserveNetworkStateHandler
import br.com.conding.tv.components.ui.TextField
import br.com.conding.tv.components.ui.TextPassword
import br.com.conding.tv.features.account.data.dto.PasswordRequestDTO
import br.com.conding.tv.features.account.data.dto.TokenResponseDTO
import br.com.conding.tv.features.account.viewmodel.AccountViewModel
import br.com.conding.tv.networking.resources.AlternativesRoutes
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import br.com.conding.tv.resources.GenericsStrings.CONFIRM_PASSWORD
import br.com.conding.tv.resources.GenericsStrings.CREATE_NEW_PASSWORD
import br.com.conding.tv.resources.GenericsStrings.EMAIL
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT
import br.com.conding.tv.resources.GenericsStrings.INVALID_PASSWORD
import br.com.conding.tv.resources.GenericsStrings.NOT_BLANK_OR_EMPTY
import br.com.conding.tv.resources.GenericsStrings.PASSWORD
import br.com.conding.tv.resources.GenericsStrings.PASSWORD_ERROR_SIZE
import br.com.conding.tv.resources.Settings.CHECK_SIZE_PASSWORD
import br.com.conding.tv.resources.Warnings.EXPIRED_CODE
import br.com.conding.tv.resources.isBlankAndEmpty
import org.koin.java.KoinJavaComponent.getKoin
import springsale.composeapp.generated.resources.Res
import springsale.composeapp.generated.resources.mail

@Composable
internal fun ResetPasswordScreen(
    emailArg: String? = EMPTY_TEXT,
    goToBackScreen: () -> Unit = {},
    goToSuccessScreen: () -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    ContentAccount(
        content = {
            var email: String by remember { mutableStateOf(value = EMPTY_TEXT) }
            emailArg?.let { email = it }
            BodyResetPasswordScreen(
                email = email,
                goToBackScreen = goToBackScreen,
                goToSuccessScreen = goToSuccessScreen,
                goToAlternativeRoutes = goToAlternativeRoutes
            )
        }
    )
}

@Composable
private fun BodyResetPasswordScreen(
    email: String,
    goToBackScreen: () -> Unit = {},
    goToSuccessScreen: () -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val viewModel: AccountViewModel = getKoin().get()
    var password: String by remember { mutableStateOf(value = EMPTY_TEXT) }
    var confirmPassword: String by remember { mutableStateOf(value = EMPTY_TEXT) }
    var observer: Triple<Boolean, Boolean, String?> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    val resetPassword = { passwordArg: String, confirmPasswordArg: String ->
        checkDataResetPassword(
            resources = Pair(email, viewModel),
            data = Pair(
                first = passwordArg,
                second = confirmPasswordArg,
            ),
            onError = { observer = it }
        )
    }
    GetDataResetPassword(
        email = email,
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
        goToBackScreen = goToBackScreen,
        onError = {
            observer = it
        },
        goToSuccessScreen = goToSuccessScreen,
        goToAlternativeRoutes = goToAlternativeRoutes
    )
    ResetPassword(
        isEnabled = observer.second,
        onClick = { resetPassword(password, confirmPassword) }
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
        icon = Res.drawable.mail,
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
    goToBackScreen: () -> Unit = {},
    goToSuccessScreen: () -> Unit,
    onError: (Triple<Boolean, Boolean, String?>) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val resetState: ObserveNetworkStateHandler<Unit> by remember { viewModel.createNewPassword }
    ObserveNetworkStateHandler(
        state = resetState,
        onError = {
            onError(Triple(first = true, second = false, third = it))
            if (it == EXPIRED_CODE) {
                goToBackScreen()
            }
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
        }
    )

    val loginState: ObserveNetworkStateHandler<TokenResponseDTO> by remember {
        viewModel.signIn
    }
    ObserveNetworkStateHandler(
        state = loginState,
        onError = {
            onError(Triple(first = true, second = false, third = it))
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            goToSuccessScreen()
        }
    )
}

@Composable
private fun ResetPassword(
    onClick: () -> Unit = {},
    isEnabled: Boolean = false
) {
    LoadingButton(
        onClick = onClick,
        isEnabled = isEnabled,
        label = CREATE_NEW_PASSWORD
    )
}
