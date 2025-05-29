package br.com.conding.tv.features.shared

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.conding.tv.components.model.Menu
import br.com.conding.tv.components.ui.IconDefault
import br.com.conding.tv.components.ui.Title
import br.com.conding.tv.features.home.factory.availableServices
import br.com.conding.tv.navigation.NavigationItems
import br.com.conding.tv.resources.changeColor
import br.com.conding.tv.resources.onBorder
import br.com.conding.tv.resources.onClickable
import br.com.conding.tv.theme.CommonColors.ITEM_SELECTED
import br.com.conding.tv.theme.Themes
import kotlinx.coroutines.launch

@Composable
fun Services(
    modifier: Modifier = Modifier,
    label: String,
    options: List<Menu> = availableServices,
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (String) -> Unit = {}
) {
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    LazyColumn(
        state = scrollState,
        modifier = modifier
            .draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { delta ->
                    coroutineScope.launch {
                        scrollState.scrollBy(delta)
                    }
                },
            )
            .padding(
                start = Themes.size.spaceSize16,
                top = Themes.size.spaceSize16,
                bottom = Themes.size.spaceSize8,
                end = Themes.size.spaceSize16
            ),
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        items(options) { item ->
            if (item.label == label) Service(
                enabled = true,
                menu = item,
                goToBackScreen = goToBackScreen,
                goToNextScreen = goToNextScreen
            ) else Service(
                menu = item,
                goToBackScreen = goToBackScreen,
                goToNextScreen = goToNextScreen
            )
        }
    }
}

@Composable
fun Service(
    enabled: Boolean = false,
    menu: Menu,
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (String) -> Unit = {}
) {
    val goToNavigation = {
        if (menu.route == NavigationItems.HOME.name) goToBackScreen() else goToNextScreen(menu.route)
    }
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
        modifier = Modifier
            .onBorder(
                onClick = goToNavigation,
                width = Themes.size.spaceSize2,
                spaceSize = Themes.size.spaceSize8,
                color = Themes.colors.primary
            )
            .changeColor(enabled = enabled)
            .width(width = Themes.size.spaceSize300)
            .padding(all = Themes.size.spaceSize16),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconDefault(
            backgroundColor = if (enabled) ITEM_SELECTED else Themes.colors.secondary,
            tint = if (enabled) Themes.colors.background else ITEM_SELECTED,
            iconName = menu.icon,
            onClick = goToNavigation
        )
        Title(
            title = menu.label,
            modifier = Modifier.onClickable(onClick = goToNavigation),
            color = if (enabled) Themes.colors.background else ITEM_SELECTED
        )
    }
}
