package br.com.conding.tv.components.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.conding.tv.getIconResource
import br.com.conding.tv.resources.IconName
import br.com.conding.tv.theme.Themes

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun DropdownMenu(
    modifier: Modifier = Modifier,
    selectedValue: String,
    items: List<String>,
    label: String,
    isError: Boolean = false,
    onValueChangedEvent: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(value = false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedValue,
            onValueChange = {},
            label = { Description(label = label) },
            isError = isError,
            leadingIcon = {
                Icon(
                    painter = getIconResource(iconName = IconName.FILTER),
                    contentDescription = label,
                    tint = Themes.colors.primary
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Themes.colors.background,
                cursorColor = Themes.colors.primary,
                focusedIndicatorColor = Themes.colors.primary,
                unfocusedIndicatorColor = Themes.colors.primary
            ),
            modifier = modifier,
            shape = RoundedCornerShape(size = Themes.size.spaceSize16),
            textStyle = Themes.typography.description().copy(color = Themes.colors.primary)
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(color = Themes.colors.background)
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    content = {
                        Description(label = item)
                    },
                    onClick = {
                        expanded = false
                        onValueChangedEvent(item)
                    }
                )
            }
        }
    }
}
