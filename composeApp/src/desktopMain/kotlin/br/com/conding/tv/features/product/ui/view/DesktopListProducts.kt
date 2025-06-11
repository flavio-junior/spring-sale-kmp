package br.com.conding.tv.features.product.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import br.com.conding.tv.components.mask.formatterMaskToMoney
import br.com.conding.tv.components.ui.Description
import br.com.conding.tv.components.ui.IconDefault
import br.com.conding.tv.features.product.data.vo.ProductResponseVO
import br.com.conding.tv.features.product.data.vo.ProductsResponseVO
import br.com.conding.tv.resources.GenericsStrings.CATEGORIES
import br.com.conding.tv.resources.GenericsStrings.NAME
import br.com.conding.tv.resources.GenericsStrings.NUMBER
import br.com.conding.tv.resources.GenericsStrings.OPTIONS
import br.com.conding.tv.resources.GenericsStrings.PRICE
import br.com.conding.tv.resources.GenericsStrings.QUANTITY
import br.com.conding.tv.resources.IconName
import br.com.conding.tv.resources.WeightSize.WEIGHT_SIZE
import br.com.conding.tv.resources.WeightSize.WEIGHT_SIZE_2
import br.com.conding.tv.resources.onBorder
import br.com.conding.tv.resources.onClickable
import br.com.conding.tv.theme.CommonColors.ITEM_SELECTED
import br.com.conding.tv.theme.NumbersUtils.NUMBER_ONE
import br.com.conding.tv.theme.Themes
import kotlinx.coroutines.launch

@Composable
internal fun DesktopListProducts(
    modifier: Modifier = Modifier,
    productsResponseVO: ProductsResponseVO? = null,
    onItemSelected: (ProductResponseVO) -> Unit = {},
    showEmptyList: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .background(color = Themes.colors.background)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        DesktopHeaderProductsPanel(modifier = Modifier.padding(top = Themes.size.spaceSize16))
        val scrollState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()
        var selectedIndex by remember { mutableStateOf(value = -1) }
        Spacer(modifier = Modifier.height(height = Themes.size.spaceSize16))
        LazyColumn(
            state = scrollState,
            modifier = modifier
                .onBorder(
                    onClick = {},
                    color = Themes.colors.primary,
                    spaceSize = Themes.size.spaceSize16,
                    width = Themes.size.spaceSize2
                )
                .draggable(
                    orientation = Orientation.Vertical,
                    state = rememberDraggableState { delta ->
                        coroutineScope.launch {
                            scrollState.scrollBy(delta)
                        }
                    },
                )
                .fillMaxWidth()
                .padding(all = Themes.size.spaceSize36)
        ) {
            productsResponseVO?.content?.let { response ->
                itemsIndexed(items = response) { index, product ->
                    DesktopItemProduct(
                        index = index,
                        selected = selectedIndex == index,
                        product = product,
                        onItemSelected = onItemSelected,
                        onDisableItem = {
                            selectedIndex = index
                        }
                    )
                }
            } ?: showEmptyList()
        }
    }
}

@Composable
internal fun DesktopHeaderProductsPanel(
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
        modifier = modifier
            .onBorder(
                onClick = {},
                color = Themes.colors.primary,
                spaceSize = Themes.size.spaceSize16,
                width = Themes.size.spaceSize1
            )
            .fillMaxWidth()
            .padding(horizontal = Themes.size.spaceSize36)
            .padding(bottom = Themes.size.spaceSize16),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Description(
            label = NUMBER,
            backgroundTransparent = true,
            modifier = modifier.weight(weight = WEIGHT_SIZE),
            textAlign = TextAlign.Center
        )
        Description(
            label = NAME,
            backgroundTransparent = true,
            modifier = modifier.weight(weight = WEIGHT_SIZE_2),
            textAlign = TextAlign.Center
        )
        Description(
            label = CATEGORIES,
            backgroundTransparent = true,
            modifier = modifier.weight(weight = WEIGHT_SIZE),
            textAlign = TextAlign.Center
        )
        Description(
            label = PRICE,
            backgroundTransparent = true,
            modifier = modifier.weight(weight = WEIGHT_SIZE),
            textAlign = TextAlign.Center
        )
        Description(
            label = QUANTITY,
            backgroundTransparent = true,
            modifier = modifier.weight(weight = WEIGHT_SIZE),
            textAlign = TextAlign.Center
        )
        Description(
            label = OPTIONS,
            backgroundTransparent = true,
            modifier = modifier.weight(weight = WEIGHT_SIZE),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
internal fun DesktopItemProduct(
    index: Int,
    selected: Boolean = false,
    modifier: Modifier = Modifier,
    product: ProductResponseVO,
    onItemSelected: (ProductResponseVO) -> Unit = {},
    onDisableItem: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .onClickable {
                onDisableItem()
            }
            .background(color = if (selected) ITEM_SELECTED else Themes.colors.background)
            .fillMaxWidth()
            .padding(vertical = Themes.size.spaceSize16),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var add = index
        add++
        Description(
            label = add.toString(),
            selectedItem = selected,
            backgroundTransparent = true,
            modifier = modifier.weight(weight = WEIGHT_SIZE),
            textAlign = TextAlign.Center,
        )
        Description(
            label = product.name,
            selectedItem = selected,
            backgroundTransparent = true,
            maxLines = NUMBER_ONE,
            modifier = modifier.weight(weight = WEIGHT_SIZE_2),
            textAlign = TextAlign.Center
        )
        Description(
            label = product.categories?.size.toString(),
            selectedItem = selected,
            backgroundTransparent = true,
            modifier = modifier.weight(weight = WEIGHT_SIZE),
            textAlign = TextAlign.Center
        )
        Description(
            label = formatterMaskToMoney(price = product.price),
            selectedItem = selected,
            backgroundTransparent = true,
            modifier = modifier.weight(weight = WEIGHT_SIZE),
            textAlign = TextAlign.Center
        )
        Description(
            label = product.quantity.toString(),
            selectedItem = selected,
            backgroundTransparent = true,
            modifier = modifier.weight(weight = WEIGHT_SIZE),
            textAlign = TextAlign.Center
        )
        IconDefault(
            iconName = IconName.VISIBILITY,
            modifier = modifier.weight(weight = WEIGHT_SIZE),
            backgroundColor = if (selected) ITEM_SELECTED else Themes.colors.background,
            tint = if (selected) Themes.colors.background else Themes.colors.primary,
            onClick = {
                onItemSelected(product)
            }
        )
    }
}
