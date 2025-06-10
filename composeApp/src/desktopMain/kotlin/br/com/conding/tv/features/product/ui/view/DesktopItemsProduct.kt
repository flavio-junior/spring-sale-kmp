package br.com.conding.tv.features.product.ui.view

import br.com.conding.tv.resources.GenericsStrings.CREATE_PRODUCT
import br.com.conding.tv.resources.GenericsStrings.DETAILS_PRODUCT
import br.com.conding.tv.resources.GenericsStrings.LIST_PRODUCTS


internal enum class DesktopItemsProduct(val text: String) {
    ListProducts(text = LIST_PRODUCTS),
    DetailsProduct(text = DETAILS_PRODUCT),
    CreateProduct(text = CREATE_PRODUCT)
}
