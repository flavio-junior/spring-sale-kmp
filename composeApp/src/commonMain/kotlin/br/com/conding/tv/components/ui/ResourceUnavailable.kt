package br.com.conding.tv.components.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.conding.tv.resources.GenericStrings.RESOURCE_UNAVAILABLE
import br.com.conding.tv.resources.IconName
import br.com.conding.tv.theme.Themes

@Composable
internal fun ResourceUnavailable(
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconDefault(
            iconName = IconName.POWER_OFF,
            size = Themes.size.spaceSize100
        )
        Spacer(modifier = Modifier.height(height = Themes.size.spaceSize16))
        Title(label = RESOURCE_UNAVAILABLE, backgroundTransparent = true)
    }
}
