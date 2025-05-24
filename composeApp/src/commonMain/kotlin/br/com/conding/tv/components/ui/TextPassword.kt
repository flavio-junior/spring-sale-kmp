package br.com.conding.tv.components.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT
import br.com.conding.tv.theme.Themes
import br.com.conding.tv.theme.Typography
import springsale.composeapp.generated.resources.Res
import springsale.composeapp.generated.resources.lock
import springsale.composeapp.generated.resources.visibility
import springsale.composeapp.generated.resources.visibility_off

@Composable
fun TextPassword(
    label: String,
    value: String,
    imeAction: ImeAction = ImeAction.Go,
    isError: Boolean = false,
    message: String = EMPTY_TEXT,
    onValueChange: (String) -> Unit,
    onGo: () -> Unit = {}
) {
    var passwordHidden by rememberSaveable { mutableStateOf(value = true) }
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = Typography(color = Themes.colors.primary).simpleText(),
        singleLine = true,
        label = {
            Description(description = label)
        },
        isError = isError,
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        leadingIcon = {
            IconDefault(
                icon = Res.drawable.lock,
                onClick = {
                    passwordHidden = !passwordHidden
                }
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(onGo = {
            onGo()
        }),
        trailingIcon = {
            IconDefault(
                icon = if (passwordHidden) Res.drawable.visibility else Res.drawable.visibility_off,
                contentDescription = if (passwordHidden) "Show password" else "Hide password",
                onClick = {
                    passwordHidden = !passwordHidden
                }
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Themes.colors.background,
            cursorColor = Themes.colors.primary,
            focusedIndicatorColor = Themes.colors.primary,
            unfocusedIndicatorColor = Themes.colors.primary
        ),
        shape = RoundedCornerShape(size = Themes.size.spaceSize16)
    )
    IsErrorMessage(isError = isError, message = message)
}
