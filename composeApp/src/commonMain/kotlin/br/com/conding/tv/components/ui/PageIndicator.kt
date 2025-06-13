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
import br.com.conding.tv.theme.NumbersUtils
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
    if (totalPages in NumbersUtils.NUMBER_ONE..currentPage) {
        reloadPage()
    } else if (totalPages == NumbersUtils.NUMBER_ZERO && currentPage > NumbersUtils.NUMBER_ZERO) {
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
            totalPages == NumbersUtils.NUMBER_ZERO -> NumbersUtils.NUMBER_ZERO
            currentPage >= totalPages -> totalPages
            else -> currentPage + NumbersUtils.NUMBER_ONE
        }
        val canGoPreviousScreen =
            totalPages > NumbersUtils.NUMBER_ZERO && currentPage > NumbersUtils.NUMBER_ZERO
        val canGoNextScreen =
            totalPages > NumbersUtils.NUMBER_ZERO && currentPage < totalPages - NumbersUtils.NUMBER_ONE
        IconDefault(
            iconName = IconName.ARROW_BACK,
            tint = if (canGoPreviousScreen) Themes.colors.primary else Themes.colors.disabled,
            modifier = Modifier
                .onBorder(
                    onClick = { if (canGoPreviousScreen) reloadPreviousPage() },
                    color = Themes.colors.primary,
                    spaceSize = Themes.size.spaceSize8,
                    width = Themes.size.spaceSize2
                )
                .size(size = Themes.size.spaceSize48)
                .padding(all = Themes.size.spaceSize16),
            onClick = { if (canGoPreviousScreen) reloadPreviousPage() }
        )
        Title(
            label = "$displayPage/$totalPages",
            backgroundTransparent = true,
            modifier = Modifier.padding(horizontal = Themes.size.spaceSize16)
        )
        IconDefault(
            iconName = IconName.ARROW_FORWARD,
            tint = if (canGoNextScreen) Themes.colors.primary else Themes.colors.disabled,
            modifier = Modifier
                .onBorder(
                    onClick = { if (canGoNextScreen) loadNextPage() },
                    color = Themes.colors.primary,
                    spaceSize = Themes.size.spaceSize8,
                    width = Themes.size.spaceSize2
                )
                .size(size = Themes.size.spaceSize48)
                .padding(all = Themes.size.spaceSize16),
            onClick = { if (canGoNextScreen) loadNextPage() }
        )
    }
}
