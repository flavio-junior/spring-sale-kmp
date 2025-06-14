package br.com.conding.tv.features.product.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.conding.tv.components.ui.Description
import br.com.conding.tv.features.product.data.vo.ProductResponseVO
import br.com.conding.tv.resources.getIconResource
import br.com.conding.tv.navigation.AppDestinations
import br.com.conding.tv.navigation.toJson
import br.com.conding.tv.resources.IconName
import br.com.conding.tv.resources.WeightSize.WEIGHT_SIZE
import br.com.conding.tv.resources.onBorder
import br.com.conding.tv.resources.onClickable
import br.com.conding.tv.theme.NumbersUtils
import br.com.conding.tv.theme.Themes

@Composable
internal fun MobileProductItem(
    productResponseVO: ProductResponseVO,
    goToNextScreen: (AppDestinations) -> Unit = {}
) {
    var deleteProduct: Boolean by remember { mutableStateOf(value = false) }
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .onBorder(
                spaceSize = Themes.size.spaceSize12,
                width = Themes.size.spaceSize2,
                color = Themes.colors.primary
            )
            .fillMaxWidth()
            .padding(all = Themes.size.spaceSize12)
    ) {
        Description(
            label = productResponseVO.name,
            backgroundTransparent = true,
            maxLines = NumbersUtils.NUMBER_ONE,
            modifier = Modifier
                .onClickable(
                    onClick = {
                        goToNextScreen(
                            AppDestinations.ProductDetails(data = productResponseVO.toJson())
                        )
                    }
                )
                .padding(all = Themes.size.spaceSize12)
                .weight(weight = WEIGHT_SIZE)
        )
        Icon(
            painter = getIconResource(iconName = IconName.DELETE),
            contentDescription = IconName.DELETE.name,
            modifier = Modifier
                .onClickable(
                    onClick = {
                        deleteProduct = true
                    }
                )
                .size(size = Themes.size.spaceSize48),
            tint = Themes.colors.primary
        )
    }
    if (deleteProduct) {
        DeleteProductBottomSheet(
            productResponseVO = productResponseVO,
            onDismiss = {
                deleteProduct = false
            }
        )
    }
}
