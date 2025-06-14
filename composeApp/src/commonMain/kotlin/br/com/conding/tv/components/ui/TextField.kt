package br.com.conding.tv.components.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import br.com.conding.tv.resources.getIconResource
import br.com.conding.tv.resources.IconName
import br.com.conding.tv.theme.Themes
import br.com.conding.tv.theme.Typography

@Composable
internal fun TextField(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String,
    value: String,
    iconName: IconName? = null,
    backgroundColor: Color = Themes.colors.background,
    textColor: Color = Themes.colors.primary,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    maxLines: Int = Int.MAX_VALUE,
    singleLine: Boolean = true,
    isError: Boolean = false,
    message: String? = null,
    onValueChange: (String) -> Unit = {},
    onGo: () -> Unit = {}
) {
    if (iconName != null) {
        TextFieldWithIcon(
            modifier = modifier,
            enabled = enabled,
            label = label,
            value = value,
            icon = getIconResource(iconName = iconName),
            backgroundColor = backgroundColor,
            textColor = textColor,
            keyboardType = keyboardType,
            imeAction = imeAction,
            maxLines = maxLines,
            singleLine = singleLine,
            isError = isError,
            message = message,
            onValueChange = onValueChange,
            onGo = onGo
        )
    } else {
        TextFieldWithoutIcon(
            modifier = modifier,
            enabled = enabled,
            label = label,
            value = value,
            backgroundColor = backgroundColor,
            textColor = textColor,
            keyboardType = keyboardType,
            imeAction = imeAction,
            maxLines = maxLines,
            singleLine = singleLine,
            isError = isError,
            message = message,
            onValueChange = onValueChange,
            onGo = onGo
        )
    }
}

@Composable
private fun TextFieldWithIcon(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String,
    value: String,
    icon: Painter,
    backgroundColor: Color,
    textColor: Color,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    maxLines: Int = Int.MAX_VALUE,
    singleLine: Boolean = true,
    isError: Boolean = false,
    message: String? = null,
    onValueChange: (String) -> Unit = {},
    onGo: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .background(color = backgroundColor)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8)
    ) {
        OutlinedTextField(
            enabled = enabled,
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            maxLines = maxLines,
            singleLine = singleLine,
            label = {
                Description(label = label, backgroundTransparent = true)
            },
            isError = isError,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    painter = icon,
                    contentDescription = label,
                    tint = textColor
                )
            },
            textStyle = Typography(backgroundTransparent = true).simpleText(),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onGo = { onGo() }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = backgroundColor,
                cursorColor = Themes.colors.primary,
                focusedIndicatorColor = Themes.colors.primary,
                unfocusedIndicatorColor = Themes.colors.primary
            ),
            shape = RoundedCornerShape(size = Themes.size.spaceSize16)
        )
        IsErrorMessage(isError = isError, message = message)
    }
}

@Composable
private fun TextFieldWithoutIcon(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String,
    value: String,
    backgroundColor: Color,
    textColor: Color,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    maxLines: Int = Int.MAX_VALUE,
    singleLine: Boolean = true,
    isError: Boolean = false,
    message: String? = null,
    onValueChange: (String) -> Unit = {},
    onGo: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .background(color = backgroundColor)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8)
    ) {
        OutlinedTextField(
            enabled = enabled,
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            maxLines = maxLines,
            singleLine = singleLine,
            label = {
                InfoText(label = label, backgroundTransparent = true)
            },
            isError = isError,
            modifier = Modifier.fillMaxWidth(),
            textStyle = Typography(backgroundTransparent = true).simpleText(),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onGo = { onGo() }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = backgroundColor,
                cursorColor = textColor,
                focusedIndicatorColor = textColor,
                unfocusedIndicatorColor = textColor
            ),
            shape = RoundedCornerShape(size = Themes.size.spaceSize16)
        )
        IsErrorMessage(isError = isError, message = message)
    }
}
