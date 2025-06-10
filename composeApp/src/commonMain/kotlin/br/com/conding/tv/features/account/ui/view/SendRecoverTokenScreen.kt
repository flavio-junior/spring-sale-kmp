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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.conding.tv.components.model.DefinitionsScreen
import br.com.conding.tv.components.ui.ContentScreen
import br.com.conding.tv.components.ui.LoadingButton
import br.com.conding.tv.components.ui.UiResponse
import br.com.conding.tv.components.ui.SimpleText
import br.com.conding.tv.components.ui.TextField
import br.com.conding.tv.components.ui.Title
import br.com.conding.tv.components.ui.UiState
import br.com.conding.tv.features.account.data.dto.EmailRequestDTO
import br.com.conding.tv.features.account.ui.viewmodel.AccountViewModel
import br.com.conding.tv.features.account.ui.viewmodel.ResetAccount
import br.com.conding.tv.navigation.AppDestinations
import br.com.conding.tv.networking.resources.HttpError
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT
import br.com.conding.tv.resources.GenericsStrings.ENTER_YOUR_ACCOUNT
import br.com.conding.tv.resources.GenericsStrings.INVALID_EMAIL
import br.com.conding.tv.resources.GenericsStrings.NOT_BLANK_OR_EMPTY
import br.com.conding.tv.resources.GenericsStrings.OR
import br.com.conding.tv.resources.GenericsStrings.RECOVER_MY_ACCOUNT
import br.com.conding.tv.resources.GenericsStrings.REGISTERED_EMAIL
import br.com.conding.tv.resources.GenericsStrings.SEND_CODE_VERIFICATION_MOBILE
import br.com.conding.tv.resources.GenericsStrings.SEND_RECOVER_TOKEN
import br.com.conding.tv.resources.IconName
import br.com.conding.tv.resources.isNotBlankAndEmpty
import br.com.conding.tv.resources.validateEmail
import org.koin.mp.KoinPlatform.getKoin

@Composable
internal fun SendRecoverTokenScreen(
    goToBackScreen: () -> Unit = {},
    goToCheckRecoverTokenToConfirmEmailScreen: (AppDestinations.CheckRecoverToken) -> Unit = {},
    goToAlternativeRoutes: (HttpError) -> Unit = {}
) {
    var changeLabel: Boolean by remember { mutableStateOf(value = false) }
    ContentScreen(
        definitionsScreen = DefinitionsScreen(
            label = RECOVER_MY_ACCOUNT
        ),
        goToBackScreen = goToBackScreen,
        extra = {
            if (it) Title(label = RECOVER_MY_ACCOUNT)
            changeLabel = it
        },
        content = {
            val viewModel: AccountViewModel = getKoin().get()
            var email: String by remember { mutableStateOf(value = EMPTY_TEXT) }
            var observer: Triple<Boolean, Boolean, String?> by remember {
                mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
            }
            val sendRecoverToken = { emailArg: String ->
                checkSendRecoverToken(
                    data = Pair(first = emailArg, second = viewModel),
                    onError = { observer = it }
                )
            }
            TextField(
                label = REGISTERED_EMAIL,
                value = email,
                isError = observer.second,
                message = observer.third ?: EMPTY_TEXT,
                iconName = IconName.MAIL,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Go,
                onValueChange = { email = it },
                onGo = { sendRecoverToken(email) }
            )
            UiResponseSendRecoverTokenScreen(
                viewModel = viewModel,
                onError = {
                    observer = it
                },
                goToCheckRecoverTokenToConfirmEmailScreen = {
                    goToCheckRecoverTokenToConfirmEmailScreen(
                        AppDestinations.CheckRecoverToken(email = email)
                    )
                },
                goToAlternativeRoutes = goToAlternativeRoutes
            )
            LoadingButton(
                onClick = { sendRecoverToken(email) },
                label = if (changeLabel) SEND_RECOVER_TOKEN else SEND_CODE_VERIFICATION_MOBILE,
                isEnabled = observer.first
            )
            SimpleText(
                label = OR,
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

private fun checkSendRecoverToken(
    data: Pair<String, AccountViewModel>,
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {}
) {
    if (data.first.isNotBlankAndEmpty()) {
        if (validateEmail(email = data.first)) {
            onError(Triple(first = true, second = false, third = EMPTY_TEXT))
            data.second.sendRecoverPassword(email = EmailRequestDTO(email = data.first))
        } else {
            onError(Triple(first = false, second = true, third = INVALID_EMAIL))
        }
    } else {
        onError(Triple(first = false, second = true, third = NOT_BLANK_OR_EMPTY))
    }
}

@Composable
private fun UiResponseSendRecoverTokenScreen(
    viewModel: AccountViewModel,
    onError: (Triple<Boolean, Boolean, String?>) -> Unit = {},
    goToCheckRecoverTokenToConfirmEmailScreen: () -> Unit = {},
    goToAlternativeRoutes: (HttpError) -> Unit = {}
) {
    val uiState: UiState<Unit> by viewModel.confirmEmailAddress.collectAsStateWithLifecycle()
    UiResponse(
        state = uiState,
        onError = {
            onError(it)
            viewModel.resetStateSignIn(resetAccount = ResetAccount.SEND_RECOVER_PASSWORD)
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            viewModel.resetStateSignIn(resetAccount = ResetAccount.SEND_RECOVER_PASSWORD)
            goToCheckRecoverTokenToConfirmEmailScreen()
        }
    )
}
