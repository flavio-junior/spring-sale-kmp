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
import br.com.conding.tv.resources.GenericsStrings.SEND_CODE_VERIFICATION
import br.com.conding.tv.resources.GenericsStrings.YOUR_MAIN_EMAIL
import br.com.conding.tv.resources.isNotBlankAndEmpty
import org.koin.java.KoinJavaComponent.getKoin
import springsale.composeapp.generated.resources.Res
import springsale.composeapp.generated.resources.mail

@Composable
internal fun SendCodeToConfirmEmailScreen(
    goToBackScreen: () -> Unit = {},
    goToCheckCodeToConfirmEmailScreen: (email: String) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    ContentAccount(
        content = {
            BodySendCodeToConfirmEmailScreen(
                goToCheckCodeToConfirmEmailScreen,
                goToBackScreen,
                goToAlternativeRoutes = goToAlternativeRoutes
            )
        }
    )
}

@Composable
private fun BodySendCodeToConfirmEmailScreen(
    goToCheckCodeToConfirmEmailScreen: (email: String) -> Unit = {},
    goToBackScreen: () -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val viewModel: AccountViewModel = getKoin().get()
    var email: String by remember { mutableStateOf(value = EMPTY_TEXT) }
    var isError: Boolean by remember { mutableStateOf(value = false) }
    var errorMessage: String? by remember { mutableStateOf(value = EMPTY_TEXT) }
    var isEnabled: Boolean by remember { mutableStateOf(value = false) }
    val checkEmail = { emailArg: String ->
        checkDataToConfirmEmail(
            data = Pair(first = emailArg, second = viewModel),
            onError = {
                isError = it.first
                isEnabled = it.second
                errorMessage = it.third
            }
        )
    }
    GetDataInputsToConfirmEmail(
        email = email,
        checkConfirmEmail = checkEmail,
        isError = isError,
        errorMessage = errorMessage,
        onValueChange = {
            email = it
        }
    )
    ObserveStateSendCodeToConfirmEmail(
        viewModel = viewModel,
        onError = {
            isError = it.first
            isEnabled = it.second
            errorMessage = it.third
        },
        goToCheckCodeToConfirmEmailScreen = { goToCheckCodeToConfirmEmailScreen(email) },
        goToAlternativeRoutes = goToAlternativeRoutes
    )
    SendCodeToConfirmEmail(isEnabled = isEnabled, checkEmail = { checkEmail(email) })
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

@Composable
private fun GetDataInputsToConfirmEmail(
    email: String,
    checkConfirmEmail: (String) -> Unit,
    isError: Boolean,
    errorMessage: String?,
    onValueChange: (String) -> Unit
) {
    var emailMutable by remember { mutableStateOf(value = email) }
    TextField(
        label = YOUR_MAIN_EMAIL,
        value = emailMutable,
        keyboardType = KeyboardType.Email,
        imeAction = ImeAction.Go,
        isError = isError,
        icon = Res.drawable.mail,
        message = errorMessage ?: EMPTY_TEXT,
        onValueChange = { emailMutable = it },
        onGo = { checkConfirmEmail(emailMutable) }
    )
    onValueChange(emailMutable)
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
            onError(Triple(first = true, second = false, third = it))
        },
        goToAlternativeRoutes = goToAlternativeRoutes,
        onSuccess = {
            onError(Triple(first = false, second = false, third = EMPTY_TEXT))
            goToCheckCodeToConfirmEmailScreen()
        }
    )
}

private fun checkDataToConfirmEmail(
    data: Pair<String, AccountViewModel>,
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {}
) {
    if (data.first.isNotBlankAndEmpty()) {
        onError(Triple(first = false, second = true, third = EMPTY_TEXT))
        data.second.confirmEmailAddress(email = EmailRequestDTO(email = data.first))
    } else {
        onError(Triple(first = true, second = false, third = NOT_BLANK_OR_EMPTY))
    }
}

@Composable
private fun SendCodeToConfirmEmail(
    isEnabled: Boolean = false,
    checkEmail: () -> Unit = {}
) {
    LoadingButton(
        onClick = {
            checkEmail()
        },
        isEnabled = isEnabled,
        label = SEND_CODE_VERIFICATION
    )
}
