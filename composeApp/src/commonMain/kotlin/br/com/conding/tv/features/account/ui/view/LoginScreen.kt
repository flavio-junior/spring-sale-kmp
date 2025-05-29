package br.com.conding.tv.features.account.ui.view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import br.com.conding.tv.components.ui.Description
import br.com.conding.tv.components.ui.LoadingButton
import br.com.conding.tv.components.ui.ObserveNetworkStateHandler
import br.com.conding.tv.components.ui.SimpleText
import br.com.conding.tv.components.ui.TextField
import br.com.conding.tv.components.ui.TextPassword
import br.com.conding.tv.features.account.data.dto.SignInRequestDTO
import br.com.conding.tv.features.account.data.dto.TokenResponseDTO
import br.com.conding.tv.features.account.data.vo.TokenResponseVO
import br.com.conding.tv.features.account.ui.viewmodel.AccountViewModel
import br.com.conding.tv.features.account.ui.viewmodel.ResetAccount
import br.com.conding.tv.networking.resources.AlternativesRoutes
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import br.com.conding.tv.resources.GenericsStrings.CREATE_ONE_ACCOUNT
import br.com.conding.tv.resources.GenericsStrings.EMAIL
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT
import br.com.conding.tv.resources.GenericsStrings.ENTER_YOUR_ACCOUNT
import br.com.conding.tv.resources.GenericsStrings.FORGOT_PASS
import br.com.conding.tv.resources.GenericsStrings.INVALID_EMAIL
import br.com.conding.tv.resources.GenericsStrings.NOT_BLANK_OR_EMPTY
import br.com.conding.tv.resources.GenericsStrings.OR
import br.com.conding.tv.resources.GenericsStrings.PASSWORD
import br.com.conding.tv.resources.GenericsStrings.VERSION
import br.com.conding.tv.resources.IconName
import br.com.conding.tv.resources.isNotBlankAndEmpty
import br.com.conding.tv.resources.isTokenExpired
import br.com.conding.tv.resources.onClickable
import br.com.conding.tv.resources.validateEmail
import br.com.conding.tv.theme.Themes
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun SignInScreen(
    goToHomeScreen: () -> Unit = {},
    goToSendRecoverPasswordScreen: () -> Unit = {},
    goToConfirmEmailAddressScreen: () -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    ContentAccount(
        content = {
            val viewModel: AccountViewModel = getKoin().get()
            var email: String by remember { mutableStateOf(value = EMPTY_TEXT) }
            var password: String by remember { mutableStateOf(value = EMPTY_TEXT) }
            var observer: Triple<Boolean, Boolean, String?> by remember {
                mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
            }
            val checkSignIn = { emailArg: String, passwordArg: String ->
                checkDataToSignIn(
                    triple = Triple(first = emailArg, second = passwordArg, third = viewModel),
                    onError = {
                        observer = it
                    }
                )
            }
            GetDataInputsSignIn(
                email = email,
                password = password,
                checkSignIn = checkSignIn,
                isError = observer,
                onValueChange = {
                    email = it.first
                    password = it.second
                }
            )
            ObserveStateSignIn(
                viewModel = viewModel,
                onError = {
                    observer = it
                },
                goToDashboardScreen = goToHomeScreen,
                goToAlternativeRoutes = goToAlternativeRoutes
            )
            SimpleText(
                text = FORGOT_PASS,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .onClickable(onClick = goToSendRecoverPasswordScreen)
                    .fillMaxWidth()
            )
            LoadingButton(
                onClick = {
                    checkSignIn(email, password)
                },
                isEnabled = observer.first,
                label = ENTER_YOUR_ACCOUNT
            )
            SimpleText(
                text = OR,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            LoadingButton(
                label = CREATE_ONE_ACCOUNT,
                onClick = goToConfirmEmailAddressScreen
            )
            Spacer(modifier = Modifier.height(height = Themes.size.spaceSize0))
            Description(description = "$VERSION 1.00")
            ObserveNetworkStateHandlerToken(
                viewModel = viewModel,
                goToDashboardScreen = goToHomeScreen
            )
        }
    )
}

@Composable
private fun GetDataInputsSignIn(
    email: String,
    password: String,
    checkSignIn: (String, String) -> Unit,
    isError: Triple<Boolean, Boolean, String?>,
    onValueChange: (Pair<String, String>) -> Unit
) {
    var emailMutable by remember { mutableStateOf(value = email) }
    var passwordMutable by remember { mutableStateOf(value = password) }
    TextField(
        label = EMAIL,
        value = emailMutable,
        iconName = IconName.MAIL,
        keyboardType = KeyboardType.Email,
        isError = isError.second,
        onValueChange = { emailMutable = it }
    )
    TextPassword(
        label = PASSWORD,
        value = passwordMutable,
        isError = isError.second,
        message = isError.third ?: EMPTY_TEXT,
        onValueChange = { passwordMutable = it },
        onGo = { checkSignIn(emailMutable, passwordMutable) }
    )
    onValueChange(Pair(first = emailMutable, second = passwordMutable))
}

@Composable
private fun ObserveStateSignIn(
    viewModel: AccountViewModel,
    onError: (Triple<Boolean, Boolean, String?>) -> Unit = {},
    goToDashboardScreen: () -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val accountState: ObserveNetworkStateHandler<TokenResponseDTO> by remember {
        viewModel.signIn
    }
    ObserveNetworkStateHandler(
        state = accountState,
        onError = {
                onError(Triple(first = false, second = true, third = it))
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            accountState.result?.let {
                viewModel.resetStateSignIn(resetAccount = ResetAccount.SIGN_IN)
                goToDashboardScreen()
            }
        }
    )
}

private fun checkDataToSignIn(
    triple: Triple<String, String, AccountViewModel>,
    onError: (Triple<Boolean, Boolean, String?>) -> Unit = {},
) {
    if (triple.first.isNotBlankAndEmpty() && triple.second.isNotBlankAndEmpty()) {
        if (validateEmail(email = triple.first)) {
            onError(Triple(first = true, second = false, third = EMPTY_TEXT))
            triple.third.signIn(SignInRequestDTO(email = triple.first, password = triple.second))
        } else {
            onError(Triple(first = false, second = true, third = INVALID_EMAIL))
        }
    } else {
        onError(Triple(first = false, second = true, third = NOT_BLANK_OR_EMPTY))
    }
}

@Composable
private fun ObserveNetworkStateHandlerToken(
    viewModel: AccountViewModel,
    goToDashboardScreen: () -> Unit = {}
) {
    LaunchedEffect(Unit) {
        viewModel.getToken()
    }
    val state: ObserveNetworkStateHandler<TokenResponseVO> by remember { viewModel.getTokenSaved }
    ObserveNetworkStateHandler(
        state = state,
        onLoading = {},
        onError = {},
        onSuccess = {
            if (state.result != null && state.result?.accessToken?.isNotBlankAndEmpty() == true) {
                val tokenResponseVO = state.result
                if (tokenResponseVO?.accessToken?.isNotBlankAndEmpty() == true) {
                    if (isTokenExpired(expirationDate = tokenResponseVO.expiration)) {
                        viewModel.cleanToken()
                    } else {
                        goToDashboardScreen()
                    }
                }
            }
        }
    )
}
