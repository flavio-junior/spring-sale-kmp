package br.com.conding.tv.components.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import br.com.conding.tv.components.model.Menu
import br.com.conding.tv.navigation.AppDestinations
import br.com.conding.tv.resources.onBorder
import br.com.conding.tv.theme.Themes

@Composable
internal fun ItemMenu(
    modifier: Modifier = Modifier,
    menu: Menu,
    goToNextScreen: (AppDestinations) -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .onBorder(
                onClick = { goToNextScreen(menu.route) },
                spaceSize = Themes.size.spaceSize16,
                width = Themes.size.spaceSize2,
                color = Themes.colors.primary
            )
            .background(color = Themes.colors.background)
            .size(size = Themes.size.spaceSize200)
            .padding(all = Themes.size.spaceSize16),
        verticalArrangement = Arrangement.Center
    ) {
        IconDefault(
            iconName = menu.icon,
            contentDescription = menu.label
        )
        Title(
            label = menu.label,
            backgroundTransparent = true,
            textAlign = TextAlign.Center
        )
    }
}