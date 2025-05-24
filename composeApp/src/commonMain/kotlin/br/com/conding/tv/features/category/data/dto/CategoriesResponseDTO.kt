package br.com.conding.tv.features.category.data.dto

import br.com.conding.tv.features.others.dto.PageableDTO
import kotlinx.serialization.Serializable

@Serializable
data class CategoriesResponseDTO(
    val totalPages: Int,
    val content: List<CategoryResponseDTO>,
    val pageable: PageableDTO
)
