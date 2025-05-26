package br.com.conding.tv.features.product.ui.view

import br.com.conding.tv.resources.GenericsStrings.CREATE_PRODUCT
import br.com.conding.tv.resources.GenericsStrings.DETAILS_PRODUCT
import br.com.conding.tv.resources.GenericsStrings.LIST_PRODUCTS


enum class ItemsProduct(val text: String) {
    ListProducts(text = LIST_PRODUCTS),
    DetailsProduct(text = DETAILS_PRODUCT),
    CreateProduct(text = CREATE_PRODUCT)
}
