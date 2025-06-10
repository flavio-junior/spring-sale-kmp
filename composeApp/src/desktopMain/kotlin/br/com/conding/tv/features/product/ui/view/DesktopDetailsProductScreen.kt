package br.com.conding.tv.features.product.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.conding.tv.components.mask.formatterMaskToMoney
import br.com.conding.tv.components.ui.Description
import br.com.conding.tv.components.ui.ResourceUnavailable
import br.com.conding.tv.components.ui.TextField
import br.com.conding.tv.features.category.ui.view.ListCategoriesAvailableResponseVO
import br.com.conding.tv.features.product.data.vo.ProductResponseVO
import br.com.conding.tv.networking.resources.HttpError
import br.com.conding.tv.resources.GenericsStrings.DETAILS_PRODUCT
import br.com.conding.tv.resources.GenericsStrings.ID
import br.com.conding.tv.resources.GenericsStrings.NAME
import br.com.conding.tv.resources.GenericsStrings.PRICE
import br.com.conding.tv.resources.GenericsStrings.QUANTITY
import br.com.conding.tv.resources.GenericsStrings.UPDATE_PRODUCT
import br.com.conding.tv.resources.WeightSize.WEIGHT_SIZE
import br.com.conding.tv.resources.WeightSize.WEIGHT_SIZE_2
import br.com.conding.tv.theme.NumbersUtils.NUMBER_ZERO
import br.com.conding.tv.theme.Themes

@Composable
internal fun DesktopDetailsProductScreen(
    productResponseVO: ProductResponseVO,
    goToAlternativeRoutes: (HttpError) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    if (productResponseVO.id > NUMBER_ZERO) {
        DesktopDetailsProductBody(
            productResponseVO = productResponseVO,
            goToAlternativeRoutes = goToAlternativeRoutes,
            onRefresh = onRefresh
        )
    } else {
        ResourceUnavailable()
    }
}

@Composable
internal fun DesktopDetailsProductBody(
    productResponseVO: ProductResponseVO,
    goToAlternativeRoutes: (HttpError) -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .background(color = Themes.colors.background)
            .padding(top = Themes.size.spaceSize16, end = Themes.size.spaceSize16)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
    ) {
        Description(label = "${DETAILS_PRODUCT}:")
        Row(
            horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
        ) {
            TextField(
                enabled = false,
                label = ID,
                value = productResponseVO.id.toString(),
                onValueChange = {},
                modifier = Modifier.weight(weight = WEIGHT_SIZE)
            )
            TextField(
                enabled = false,
                label = NAME,
                value = productResponseVO.name,
                onValueChange = {},
                modifier = Modifier.weight(weight = WEIGHT_SIZE_2)
            )
            TextField(
                enabled = false,
                label = PRICE,
                value = formatterMaskToMoney(price = productResponseVO.price),
                onValueChange = {},
                modifier = Modifier.weight(weight = WEIGHT_SIZE)
            )
            TextField(
                enabled = false,
                label = QUANTITY,
                value = productResponseVO.quantity.toString(),
                onValueChange = {},
                modifier = Modifier.weight(weight = WEIGHT_SIZE)
            )
        }
        ListCategoriesAvailableResponseVO(categories = productResponseVO.categories)
        Description(label = UPDATE_PRODUCT)
        DesktopUpdateProduct(
            id = productResponseVO.id,
            goToAlternativeRoutes = goToAlternativeRoutes,
            onRefresh = onRefresh
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16),
            modifier = Modifier.weight(weight = WEIGHT_SIZE)
        ) {
            DesktopUpdatePriceProduct(
                id = productResponseVO.id,
                goToAlternativeRoutes = goToAlternativeRoutes,
                modifier = Modifier
                    .weight(weight = WEIGHT_SIZE_2),
                onRefresh = onRefresh
            )
            DesktopRestockProduct(
                id = productResponseVO.id,
                goToAlternativeRoutes = goToAlternativeRoutes,
                modifier = Modifier.weight(weight = WEIGHT_SIZE_2),
                onRefresh = onRefresh
            )
        }
    }
}
