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
import br.com.conding.tv.theme.Themes

@Composable
internal fun PageIndicator(
    modifier: Modifier = Modifier,
    currentPage: Int,
    totalPages: Int,
    loadNextPage: () -> Unit = {},
    reloadPreviousPage: () -> Unit = {},
    reloadPage: () -> Unit = {}
) {
    if (totalPages in 1..currentPage) {
        reloadPage()
    } else if (totalPages == 0 && currentPage > 0) {
        reloadPage()
    }
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
        val displayPage = when {
            totalPages == 0 -> 0
            currentPage >= totalPages -> totalPages
            else -> currentPage + 1
        }
        val canGoPrevious = totalPages > 0 && currentPage > 0
        val canGoNext = totalPages > 0 && currentPage < totalPages - 1
        IconDefault(
            iconName = IconName.ARROW_BACK,
            modifier = Modifier
                .onBorder(
                    onClick = { if (canGoPrevious) reloadPreviousPage() },
                    color = Themes.colors.primary,
                    spaceSize = Themes.size.spaceSize8,
                    width = Themes.size.spaceSize2
                )
                .size(size = Themes.size.spaceSize48)
                .padding(all = Themes.size.spaceSize16),
            onClick = { if (canGoPrevious) reloadPreviousPage() }
        )
        Title(
            label = "$displayPage/$totalPages",
            backgroundTransparent = true,
            modifier = Modifier.padding(horizontal = Themes.size.spaceSize16)
        )
        IconDefault(
            iconName = IconName.ARROW_FORWARD,
            modifier = Modifier
                .onBorder(
                    onClick = { if (canGoNext) loadNextPage() },
                    color = Themes.colors.primary,
                    spaceSize = Themes.size.spaceSize8,
                    width = Themes.size.spaceSize2
                )
                .size(size = Themes.size.spaceSize48)
                .padding(all = Themes.size.spaceSize16),
            onClick = { if (canGoNext) loadNextPage() }
        )
    }
}
