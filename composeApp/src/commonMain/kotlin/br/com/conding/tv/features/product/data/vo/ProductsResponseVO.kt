package br.com.conding.tv.features.product.data.vo

import br.com.conding.tv.features.others.vo.PageableVO

data class ProductsResponseVO(
    val totalPages: Int,
    val content: List<ProductResponseVO>,
    val pageable: PageableVO
)
