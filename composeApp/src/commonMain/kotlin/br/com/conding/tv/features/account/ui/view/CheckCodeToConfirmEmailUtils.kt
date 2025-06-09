package br.com.conding.tv.features.account.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import br.com.conding.tv.components.ui.LoadingButton
import br.com.conding.tv.components.ui.OtpTextField
import br.com.conding.tv.components.ui.SimpleButton
import br.com.conding.tv.features.account.ui.viewmodel.AccountViewModel
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT
import br.com.conding.tv.resources.GenericsStrings.NOT_BLANK_OR_EMPTY
import br.com.conding.tv.resources.GenericsStrings.PAST_CODE
import br.com.conding.tv.resources.GenericsStrings.VERIFY_CODE_SENT
import br.com.conding.tv.theme.NumbersUtils.NUMBER_FOUR

@Composable
internal fun GetCodeToConfirmEmail(
    code: String,
    onError: Pair<Boolean, String?>,
    onValueChange: (String) -> Unit = {},
    checkCode: (String) -> Unit = {}
) {
    var codeMutable: String by remember { mutableStateOf(value = code) }
    OtpTextField(
        otpText = codeMutable,
        onOtpTextChange = { value, _ ->
            codeMutable = value
        },
        isError = onError.first,
        message = onError.second ?: EMPTY_TEXT,
        onGo = { checkCode(code) }
    )
    onValueChange(codeMutable)
}

enum class TypeScreen {
    RECOVER_TOKEN,
    SIGN_UP
}

internal fun checkCodeToConfirmEmail(
    data: Pair<String, AccountViewModel>,
    route: TypeScreen,
    onError: (Triple<Boolean, Boolean, String>) -> Unit = {}
) {
    if (data.first.length >= NUMBER_FOUR) {
        onError(Triple(first = false, second = true, third = EMPTY_TEXT))
        if (route == TypeScreen.RECOVER_TOKEN) {
            data.second.checkRecoverPassword(code = data.first)
        } else {
            data.second.checkCodeAlreadyExists(code = data.first)
        }
    } else {
        onError(Triple(first = true, second = false, third = NOT_BLANK_OR_EMPTY))
    }
}

@Composable
internal fun PasteCodeToConfirmEmail(
    onClick: () -> Unit = {}
) {
    SimpleButton(
        onClick = onClick,
        label = PAST_CODE
    )
}

@Composable
internal fun CheckCodeToConfirmEmail(
    onClick: () -> Unit = {},
    isEnabled: Boolean = false
) {
    LoadingButton(
        onClick = onClick,
        isEnabled = isEnabled,
        label = VERIFY_CODE_SENT
    )
}
