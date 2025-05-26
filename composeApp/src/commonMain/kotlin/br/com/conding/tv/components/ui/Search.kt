package br.com.conding.tv.components.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT
import br.com.conding.tv.resources.GenericsStrings.SEARCH
import br.com.conding.tv.resources.onClickable
import br.com.conding.tv.theme.Themes
import br.com.conding.tv.theme.Typography
import springsale.composeapp.generated.resources.Res
import springsale.composeapp.generated.resources.search

@Composable
fun Search(
    modifier: Modifier = Modifier,
    value: String,
    isError: Boolean = false,
    message: String = EMPTY_TEXT,
    onValueChange: (String) -> Unit = {},
    onGo: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .background(color = Themes.colors.background)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        BodySearch(
            value = value,
            label = SEARCH,
            isError = isError,
            message = message,
            onValueChange = onValueChange,
            onGo = onGo
        )
    }
}

@Composable
private fun BodySearch(
    value: String = EMPTY_TEXT,
    label: String = EMPTY_TEXT,
    isError: Boolean = false,
    message: String = EMPTY_TEXT,
    onValueChange: (String) -> Unit = {},
    onGo: () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        var showIconSearchButton by remember { mutableStateOf(value = false) }
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
                showIconSearchButton = it.isNotBlank()
            },
            singleLine = true,
            label = {
                InfoText(text = label, color = Themes.colors.primary)
            },
            isError = isError,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                if (showIconSearchButton) {
                    IconDefault(
                        icon = Res.drawable.search,
                        contentDescription = SEARCH,
                        modifier = Modifier.onClickable(onClick = onGo),
                        onClick = onGo
                    )
                }
            },
            textStyle = Typography(color = Themes.colors.primary).infoText(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Go
            ),
            keyboardActions = KeyboardActions(
                onGo = { onGo() }
            ),
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
}
