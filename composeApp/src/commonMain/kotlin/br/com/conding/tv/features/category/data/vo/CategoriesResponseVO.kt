package br.com.digital.store.features.category.data.vo

import br.com.conding.tv.features.category.data.vo.CategoryResponseVO
import br.com.conding.tv.features.others.vo.PageableVO

data class CategoriesResponseVO(
    val totalPages: Int,
    val content: List<CategoryResponseVO>,
    val pageable: PageableVO
)
