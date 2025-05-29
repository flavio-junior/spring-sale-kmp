package br.com.conding.tv.components.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.conding.tv.resources.IconName
import br.com.conding.tv.resources.Settings.ASC
import br.com.conding.tv.resources.Settings.DESC
import br.com.conding.tv.resources.onBorder
import br.com.conding.tv.theme.Themes

@Composable
fun SortBy(
    onClick: (String) -> Unit = {}
) {
    var sortBy: Boolean by remember { mutableStateOf(value = false) }
    IconDefault(
        iconName = if (sortBy) IconName.ARROW_UPWARD else IconName.DOWNWARD,
        modifier = Modifier
            .onBorder(
                onClick = {},
                color = Themes.colors.primary,
                spaceSize = Themes.size.spaceSize16,
                width = Themes.size.spaceSize2
            )
            .size(size = Themes.size.spaceSize64)
            .padding(all = Themes.size.spaceSize8),
        onClick = {
            sortBy = !sortBy
            onClick(if (sortBy) DESC else ASC)
        }
    )
}
