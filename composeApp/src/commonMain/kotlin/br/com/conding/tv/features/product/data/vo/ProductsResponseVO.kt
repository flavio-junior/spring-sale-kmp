package br.com.conding.tv.features.product.data.vo

import br.com.conding.tv.features.others.vo.PageableVO

data class ProductsResponseVO(
    val totalPages: Int? = 0,
    val content: List<ProductResponseVO>? = null,
    val pageable: PageableVO? = null
)
