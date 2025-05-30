package br.com.conding.tv.components.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import br.com.conding.tv.getIconResource
import br.com.conding.tv.resources.IconName
import br.com.conding.tv.resources.WeightSize.WEIGHT_SIZE
import br.com.conding.tv.resources.onClickable
import br.com.conding.tv.theme.Themes

@Composable
fun Header(
    label: String,
    textAlign: TextAlign = TextAlign.Start,
    goToBackScreen: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
        modifier = Modifier
            .background(color = Themes.colors.background)
            .fillMaxWidth()
            .height(height = Themes.size.spaceSize64)
            .padding(horizontal = Themes.size.spaceSize36),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (textAlign == TextAlign.Start) {
            Icon(
                painter = getIconResource(iconName = IconName.ARROW_BACK),
                contentDescription = label,
                modifier = Modifier.onClickable(onClick = goToBackScreen),
                tint = Themes.colors.primary
            )
        }
        Description(
            description = label,
            color = Themes.colors.primary,
            modifier = Modifier.weight(weight = WEIGHT_SIZE),
            textAlign = textAlign
        )
    }
}
