package br.com.conding.tv.features.category.ui.view

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import br.com.conding.tv.components.ui.Description
import br.com.conding.tv.components.ui.Tag
import br.com.conding.tv.features.category.data.vo.CategoryResponseVO
import br.com.conding.tv.resources.GenericsStrings.CATEGORIES
import br.com.conding.tv.theme.Themes
import kotlinx.coroutines.launch

@Composable
internal fun ListCategoriesAvailableResponseVO(
    categories: List<CategoryResponseVO>? = null
) {
    Description(label = "$CATEGORIES:", backgroundTransparent = true)
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
    ) {
        val scrollState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
            state = scrollState,
            modifier = Modifier
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        coroutineScope.launch {
                            scrollState.scrollBy(-delta)
                        }
                    }
                )
        ) {
            categories?.let {
                items(it) { category ->
                    Tag(
                        text = category.name,
                        value = category,
                        enabled = false
                    )
                }
            }
        }
    }
}
