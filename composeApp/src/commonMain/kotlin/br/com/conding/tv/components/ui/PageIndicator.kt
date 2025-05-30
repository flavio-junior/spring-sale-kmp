package br.com.conding.tv.components.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.conding.tv.resources.IconName
import br.com.conding.tv.resources.onBorder
import br.com.conding.tv.theme.NumbersUtils.NUMBER_ONE
import br.com.conding.tv.theme.NumbersUtils.NUMBER_ZERO
import br.com.conding.tv.theme.Themes

@Composable
fun PageIndicator(
    modifier: Modifier = Modifier,
    currentPage: Int,
    totalPages: Int,
    loadNextPage: () -> Unit = {},
    reloadPreviousPage: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth()
            .padding(
                top = Themes.size.spaceSize16,
                start = Themes.size.spaceSize16,
                end = Themes.size.spaceSize16
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val newValue = if (currentPage == NUMBER_ZERO && totalPages == NUMBER_ZERO) {
            NUMBER_ZERO
        } else if (currentPage == NUMBER_ZERO && totalPages == NUMBER_ONE) {
            NUMBER_ONE
        } else {
            currentPage + NUMBER_ONE
        }
        IconDefault(
            iconName = IconName.ARROW_BACK,
            modifier = Modifier
                .onBorder(
                    onClick = reloadPreviousPage,
                    color = Themes.colors.primary,
                    spaceSize = Themes.size.spaceSize8,
                    width = Themes.size.spaceSize2
                )
                .size(size = Themes.size.spaceSize48)
                .padding(all = Themes.size.spaceSize16),
            onClick = reloadPreviousPage
        )
        Title(
            label = "$newValue/$totalPages",
            modifier = Modifier.padding(horizontal = Themes.size.spaceSize16)
        )
        IconDefault(
            iconName = IconName.ARROW_FORWARD,
            modifier = Modifier
                .onBorder(
                    onClick = loadNextPage,
                    color = Themes.colors.primary,
                    spaceSize = Themes.size.spaceSize8,
                    width = Themes.size.spaceSize2
                )
                .size(size = Themes.size.spaceSize48)
                .padding(all = Themes.size.spaceSize16),
            onClick = loadNextPage
        )
    }
}
