package br.com.conding.tv.features.account.ui.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import br.com.conding.tv.components.ui.LoadingButton
import br.com.conding.tv.components.ui.ObserveNetworkStateHandler
import br.com.conding.tv.components.ui.SimpleText
import br.com.conding.tv.components.ui.TextField
import br.com.conding.tv.components.ui.Title
import br.com.conding.tv.features.account.data.dto.EmailRequestDTO
import br.com.conding.tv.features.account.ui.viewmodel.AccountViewModel
import br.com.conding.tv.features.account.ui.viewmodel.ResetAccount
import br.com.conding.tv.navigation.CheckCodeToConfirmEmail
import br.com.conding.tv.networking.resources.AlternativesRoutes
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import br.com.conding.tv.resources.GenericsStrings.CREATE_MY_ACCOUNT
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT
import br.com.conding.tv.resources.GenericsStrings.ENTER_YOUR_ACCOUNT
import br.com.conding.tv.resources.GenericsStrings.INVALID_EMAIL
import br.com.conding.tv.resources.GenericsStrings.NOT_BLANK_OR_EMPTY
import br.com.conding.tv.resources.GenericsStrings.OR
import br.com.conding.tv.resources.GenericsStrings.SEND_CODE_VERIFICATION
import br.com.conding.tv.resources.GenericsStrings.YOUR_MAIN_EMAIL
import br.com.conding.tv.resources.IconName
import br.com.conding.tv.resources.isNotBlankAndEmpty
import br.com.conding.tv.resources.validateEmail
import org.koin.mp.KoinPlatform.getKoin

@Composable
internal fun SendCodeToConfirmEmailScreen(
    goToBackScreen: () -> Unit = {},
    goToCheckCodeToConfirmEmailScreen: (CheckCodeToConfirmEmail) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    ContentAccount(
        label = CREATE_MY_ACCOUNT,
        goToBackScreen = goToBackScreen,
        extra = {
            if (it) Title(title = CREATE_MY_ACCOUNT)
        },
        content = {
            val viewModel: AccountViewModel = getKoin().get()
            var email: String by remember { mutableStateOf(value = EMPTY_TEXT) }
            var observer: Triple<Boolean, Boolean, String?> by remember {
                mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
            }
            val checkEmail = { emailArg: String ->
                checkDataToConfirmEmail(
                    data = Pair(first = emailArg, second = viewModel),
                    onError = {
                        observer = it
                    }
                )
            }
            TextField(
                label = YOUR_MAIN_EMAIL,
                value = email,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Go,
                iconName = IconName.MAIL,
                isError = observer.second,
                message = observer.third ?: EMPTY_TEXT,
                onValueChange = { email = it },
                onGo = { checkEmail(email) }
            )
            ObserveStateSendCodeToConfirmEmail(
                viewModel = viewModel,
                onError = {
                    observer = it
                },
                goToCheckCodeToConfirmEmailScreen = {
                    goToCheckCodeToConfirmEmailScreen(CheckCodeToConfirmEmail(email = email))
                },
                goToAlternativeRoutes = goToAlternativeRoutes
            )
            LoadingButton(
                onClick = {
                    checkEmail(email)
                },
                isEnabled = observer.first,
                label = SEND_CODE_VERIFICATION
            )
            SimpleText(
                text = OR,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            LoadingButton(
                label = ENTER_YOUR_ACCOUNT,
                onClick = goToBackScreen
            )
        }
    )
}

@Composable
private fun ObserveStateSendCodeToConfirmEmail(
    viewModel: AccountViewModel,
    onError: (Triple<Boolean, Boolean, String?>) -> Unit = {},
    goToCheckCodeToConfirmEmailScreen: () -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val state: ObserveNetworkStateHandler<Unit> by remember { viewModel.confirmEmailAddress }
    ObserveNetworkStateHandler(
        state = state,
        onError = {
            onError(Triple(first = false, second = true, third = it))
            viewModel.resetStateSignIn(resetAccount = ResetAccount.CONFIRM_EMAIL_ADDRESS)
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            viewModel.resetStateSignIn(resetAccount = ResetAccount.CONFIRM_EMAIL_ADDRESS)
            goToCheckCodeToConfirmEmailScreen()
        }
    )
}

private fun checkDataToConfirmEmail(
    data: Pair<String, AccountViewModel>,
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {}
) {
    if (data.first.isNotBlankAndEmpty()) {
        if (validateEmail(email = data.first)) {
            onError(Triple(first = true, second = false, third = EMPTY_TEXT))
            data.second.confirmEmailAddress(email = EmailRequestDTO(email = data.first))
        } else {
            onError(Triple(first = false, second = true, third = INVALID_EMAIL))
        }
    } else {
        onError(Triple(first = false, second = true, third = NOT_BLANK_OR_EMPTY))
    }
}
