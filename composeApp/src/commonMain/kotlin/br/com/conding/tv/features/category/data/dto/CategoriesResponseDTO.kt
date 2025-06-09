package br.com.conding.tv.features.category.data.dto

import br.com.conding.tv.features.others.dto.PageableDTO
import kotlinx.serialization.Serializable

@Serializable
internal data class CategoriesResponseDTO(
    val totalPages: Int? = 0,
    val content: List<CategoryResponseDTO>? = null,
    val pageable: PageableDTO? = null
)
