package br.com.conding.tv.features.product.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import br.com.conding.tv.components.ui.Description
import br.com.conding.tv.features.product.data.vo.ProductResponseVO
import br.com.conding.tv.networking.resources.AlternativesRoutes
import br.com.conding.tv.resources.WeightSize.WEIGHT_SIZE
import br.com.conding.tv.theme.NumbersUtils.NUMBER_ONE
import br.com.conding.tv.theme.NumbersUtils.NUMBER_TWO
import br.com.conding.tv.theme.Themes
import kotlinx.coroutines.launch

@Composable
internal fun ProductsTabs(
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { ItemsProduct.entries.size })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
    var productsResponseVO: ProductResponseVO by remember { mutableStateOf(value = ProductResponseVO()) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = Themes.size.spaceSize36)
    ) {
        ScrollableTabRow(
            backgroundColor = Color.Transparent,
            selectedTabIndex = selectedTabIndex.value,
            modifier = Modifier
                .background(color = Themes.colors.background)
                .fillMaxWidth()
        ) {
            ItemsProduct.entries.forEachIndexed { index, currentTab ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(currentTab.ordinal)
                        }
                    },
                    text = {
                        Description(label = currentTab.text)
                    }
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(weight = WEIGHT_SIZE)
        ) {
            Box(
                modifier = Modifier
                    .background(color = Themes.colors.background)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                ProductTabMain(
                    index = it,
                    productsResponseVO = productsResponseVO,
                    onItemSelected = {
                        productsResponseVO = it
                        scope.launch {
                            pagerState.animateScrollToPage(page = NUMBER_ONE)
                        }
                    },
                    onToCreateNewProduct = {
                        scope.launch {
                            pagerState.animateScrollToPage(page = + NUMBER_TWO)
                        }
                    },
                    goToAlternativeRoutes = goToAlternativeRoutes,
                    onRefresh = {
                        scope.launch {
                            pagerState.animateScrollToPage(page = - it)
                        }
                    }
                )
            }
        }
    }
}
