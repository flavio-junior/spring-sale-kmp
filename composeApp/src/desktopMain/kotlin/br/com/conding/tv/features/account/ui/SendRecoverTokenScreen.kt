package br.com.conding.tv.features.account.ui

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
import br.com.conding.tv.features.account.data.dto.EmailRequestDTO
import br.com.conding.tv.features.account.viewmodel.AccountViewModel
import br.com.conding.tv.networking.resources.AlternativesRoutes
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import br.com.conding.tv.resources.GenericsStrings.CREATE_ONE_ACCOUNT
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT
import br.com.conding.tv.resources.GenericsStrings.NOT_BLANK_OR_EMPTY
import br.com.conding.tv.resources.GenericsStrings.OR
import br.com.conding.tv.resources.GenericsStrings.REGISTERED_EMAIL
import br.com.conding.tv.resources.GenericsStrings.SEND_RECOVER_TOKEN
import br.com.conding.tv.resources.isNotBlankAndEmpty
import org.koin.java.KoinJavaComponent.getKoin
import springsale.composeapp.generated.resources.Res
import springsale.composeapp.generated.resources.mail

@Composable
internal fun SendRecoverTokenScreen(
    goToBackScreen: () -> Unit = {},
    goToCheckRecoverTokenToConfirmEmailScreen: (email: String) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    ContentAccount(
        content = {
            val viewModel: AccountViewModel = getKoin().get()
            var email: String by remember { mutableStateOf(value = EMPTY_TEXT) }
            var state: Triple<Boolean, Boolean, String?> by remember {
                mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
            }
            val sendRecoverToken = { emailArg: String ->
                checkSendRecoverToken(
                    data = Pair(first = emailArg, second = viewModel),
                    onError = { state = it }
                )
            }
            GetDataSendRecoverToken(
                email = email,
                onError = Pair(state.first, state.third),
                sendRecoverToken = { sendRecoverToken(it) },
                onValueChange = { email = it }
            )
            ObserveNetworkStateHandlerSendRecoverToken(
                viewModel = viewModel,
                onError = {
                    state = it
                },
                goToCheckRecoverTokenToConfirmEmailScreen = {
                    goToCheckRecoverTokenToConfirmEmailScreen(email)
                },
                goToAlternativeRoutes = goToAlternativeRoutes
            )
            SendRecoverToken(
                onClick = { sendRecoverToken(email) },
                isEnabled = state.second,
            )
            SimpleText(
                text = OR,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            LoadingButton(
                label = CREATE_ONE_ACCOUNT,
                onClick = goToBackScreen
            )
        }
    )
}

@Composable
private fun GetDataSendRecoverToken(
    email: String,
    onError: Pair<Boolean, String?>,
    sendRecoverToken: (String) -> Unit = {},
    onValueChange: (String) -> Unit = {}
) {
    var emailMutable: String by remember { mutableStateOf(value = email) }
    TextField(
        label = REGISTERED_EMAIL,
        value = emailMutable,
        isError = onError.first,
        message = onError.second ?: EMPTY_TEXT,
        icon = Res.drawable.mail,
        keyboardType = KeyboardType.Email,
        imeAction = ImeAction.Go,
        onValueChange = { emailMutable = it },
        onGo = { sendRecoverToken(emailMutable) }
    )
    onValueChange(emailMutable)
}

private fun checkSendRecoverToken(
    data: Pair<String, AccountViewModel>,
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {}
) {
    if (data.first.isNotBlankAndEmpty()) {
        onError(Triple(first = false, second = true, third = EMPTY_TEXT))
        data.second.sendRecoverPassword(email = EmailRequestDTO(email = data.first))
    } else {
        onError(Triple(first = true, second = false, third = NOT_BLANK_OR_EMPTY))
    }
}

@Composable
private fun ObserveNetworkStateHandlerSendRecoverToken(
    viewModel: AccountViewModel,
    onError: (Triple<Boolean, Boolean, String?>) -> Unit = {},
    goToCheckRecoverTokenToConfirmEmailScreen: () -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val accountState: ObserveNetworkStateHandler<Unit> by remember { viewModel.confirmEmailAddress }
    ObserveNetworkStateHandler(
        state = accountState,
        onError = {
            onError(Triple(first = true, second = false, third = it))
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            goToCheckRecoverTokenToConfirmEmailScreen()
        }
    )
}

@Composable
private fun SendRecoverToken(
    onClick: () -> Unit = {},
    isEnabled: Boolean = false
) {
    LoadingButton(
        onClick = onClick,
        label = SEND_RECOVER_TOKEN,
        isEnabled = isEnabled
    )
}
