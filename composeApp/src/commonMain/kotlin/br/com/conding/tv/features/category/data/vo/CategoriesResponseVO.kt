package br.com.conding.tv.features.category.data.vo

import br.com.conding.tv.features.others.vo.PageableVO

internal data class CategoriesResponseVO(
    val totalPages: Int? = null,
    val content: List<CategoryResponseVO>? = null,
    val pageable: PageableVO? = null
)
