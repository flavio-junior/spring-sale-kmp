package br.com.conding.tv.components.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import br.com.conding.tv.resources.onBorder
import br.com.conding.tv.theme.Themes

@Composable
fun LoadingButton(
    modifier: Modifier = Modifier,
    background: Color = Themes.colors.secondary,
    label: String,
    isEnabled: Boolean = false,
    onClick: () -> Unit = {}
) {
    var enabled: Boolean by remember { mutableStateOf(value = false) }
    enabled = isEnabled
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .onBorder(
                onClick = onClick,
                spaceSize = Themes.size.spaceSize16,
                width = Themes.size.spaceSize2,
                color = Themes.colors.primary
            )
            .background(color = background)
            .padding(all = Themes.size.spaceSize18)
            .fillMaxWidth()
    ) {
        if (enabled) {
            CircularProgressIndicator()
        } else {
            Description(description = label.lowercase())
        }
    }
}

