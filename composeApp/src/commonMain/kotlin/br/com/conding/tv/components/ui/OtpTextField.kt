package br.com.conding.tv.components.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT
import br.com.conding.tv.theme.Themes

@Composable
internal fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 4,
    onOtpTextChange: (String, Boolean) -> Unit,
    isError: Boolean = false,
    message: String = EMPTY_TEXT,
    onGo: () -> Unit = {}
) {
    BasicTextField(
        modifier = modifier,
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = {
            if (it.text.length <= otpCount) {
                onOtpTextChange.invoke(it.text, it.text.length == otpCount)
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Go
        ),
        keyboardActions = KeyboardActions(onGo = {
            onGo()
        }),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(otpCount) { index ->
                    val position = when {
                        index >= otpText.length -> EMPTY_TEXT
                        else -> otpText[index]
                    }
                    Title(
                        modifier = if (isError) {
                            modifier
                                .border(
                                    width = Themes.size.spaceSize2,
                                    color = Themes.colors.error,
                                    shape = RoundedCornerShape(Themes.size.spaceSize16)
                                )
                                .width(width = Themes.size.spaceSize64)
                                .padding(all = Themes.size.spaceSize16)
                        } else {
                            modifier
                                .border(
                                    width = Themes.size.spaceSize2,
                                    color = Themes.colors.primary,
                                    shape = RoundedCornerShape(Themes.size.spaceSize16)
                                )
                                .width(width = Themes.size.spaceSize64)
                                .padding(all = Themes.size.spaceSize16)
                        },
                        label = position.toString(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.width(width = Themes.size.spaceSize16))
                }
            }
        }
    )
    if (isError && message.isNotEmpty()) {
        InfoText(modifier = Modifier.fillMaxWidth(), label = message, textAlign = TextAlign.Start)
    }
}
