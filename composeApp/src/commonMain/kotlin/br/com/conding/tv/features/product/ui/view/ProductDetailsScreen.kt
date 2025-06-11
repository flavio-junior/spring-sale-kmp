package br.com.conding.tv.features.product.ui.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.conding.tv.components.model.DefinitionsScreen
import br.com.conding.tv.components.ui.ContentScreen
import br.com.conding.tv.components.ui.Price
import br.com.conding.tv.components.ui.SimpleButton
import br.com.conding.tv.components.ui.TextField
import br.com.conding.tv.features.category.ui.view.ListCategoriesAvailableResponseVO
import br.com.conding.tv.features.product.data.vo.ProductResponseVO
import br.com.conding.tv.networking.resources.HttpError
import br.com.conding.tv.resources.GenericsStrings
import br.com.conding.tv.resources.GenericsStrings.PRICE
import br.com.conding.tv.resources.IconName
import br.com.conding.tv.theme.Themes

@Composable
internal fun ProductDetailsScreen(
    goToBackScreen: () -> Unit = {},
    productResponseVO: ProductResponseVO,
    goToAlternativeRoutes: (HttpError) -> Unit = {}
) {
    ContentScreen(
        definitionsScreen = DefinitionsScreen(
            label = GenericsStrings.DETAILS_PRODUCT,
            scroll = true
        ),
        goToBackScreen = goToBackScreen,
        content = {
            TextField(
                enabled = false,
                label = GenericsStrings.NAME,
                value = productResponseVO.name,
                iconName = IconName.EDIT
            )
            ListCategoriesAvailableResponseVO(
                categories = productResponseVO.categories,
                modifier = Modifier.fillMaxWidth()
            )
            Price(
                enabled = false,
                label = PRICE,
                value = productResponseVO.price.toString()
            )
            TextField(
                enabled = false,
                label = GenericsStrings.QUANTITY,
                value = productResponseVO.quantity.toString(),
                iconName = IconName.BOX
            )
            SimpleButton(
                label = GenericsStrings.UPDATE_PRODUCT_SINGLE,
                onClick = {}
            )
            SimpleButton(
                label = GenericsStrings.UPDATE_PRICE_PRODUCT,
                onClick = {}
            )
            RestockProduct(
                goToBackScreen = goToBackScreen,
                productResponseVO = productResponseVO,
                goToAlternativeRoutes = goToAlternativeRoutes
            )
            DeleteProduct(
                goToBackScreen = goToBackScreen,
                productResponseVO = productResponseVO,
                goToAlternativeRoutes = goToAlternativeRoutes
            )
        }
    )
}

@Composable
private fun RestockProduct(
    goToBackScreen: () -> Unit = {},
    productResponseVO: ProductResponseVO,
    goToAlternativeRoutes: (HttpError) -> Unit = {}
) {
    var openDialog: Boolean by remember { mutableStateOf(value = false) }
    SimpleButton(
        label = GenericsStrings.RESTOCK_PRODUCT,
        onClick = {
            openDialog = true
        }
    )
    if (openDialog) {
        RestockProductBottomSheet(
            productResponseVO = productResponseVO,
            goToAlternativeRoutes = {
                openDialog = false
                goToAlternativeRoutes(it)
            },
            onDismiss = {
                openDialog = false
            },
            goToBackScreen = {
                goToBackScreen()
            }
        )
    }
}

@Composable
private fun DeleteProduct(
    goToBackScreen: () -> Unit = {},
    productResponseVO: ProductResponseVO,
    goToAlternativeRoutes: (HttpError) -> Unit = {}
) {
    var openDialog: Boolean by remember { mutableStateOf(value = false) }
    SimpleButton(
        label = GenericsStrings.DELETE_PRODUCT,
        onClick = {
            openDialog = true
        },
        background = Themes.colors.error
    )
    if (openDialog) {
        DeleteProductBottomSheet(
            productResponseVO = productResponseVO,
            goToAlternativeRoutes = {
                openDialog = false
                goToAlternativeRoutes(it)
            },
            onDismiss = {
                openDialog = false
            },
            goToBackScreen = {
                goToBackScreen()
            }
        )
    }
}
