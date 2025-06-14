package br.com.conding.tv.components.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import br.com.conding.tv.resources.getIconResource
import br.com.conding.tv.resources.IconName
import br.com.conding.tv.resources.onBorder
import br.com.conding.tv.theme.Themes

@Composable
internal fun CheckBox(
    label: String,
    isSelected: Boolean,
    onCheckedChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable(onClick = { onCheckedChange(label) }),
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .onBorder(
                    onClick = {
                        onCheckedChange(label)
                    },
                    spaceSize = Themes.size.spaceSize16,
                    width = Themes.size.spaceSize3,
                    color = Color.Black
                )
                .size(size = Themes.size.spaceSize48),
            contentAlignment = Alignment.Center
        ) {
            if (isSelected) {
                Icon(
                    painter = getIconResource(iconName = IconName.CHECK_BOX),
                    contentDescription = "$label selected",
                    tint = Themes.colors.primary,
                    modifier = Modifier.size(size = Themes.size.spaceSize40)
                )
            }
        }
        Description(
            label = label,
            backgroundTransparent = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
