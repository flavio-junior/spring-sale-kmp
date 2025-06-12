package br.com.conding.tv.features.product.data.dto

import br.com.conding.tv.features.category.data.dto.CategoryResponseDTO
import kotlinx.serialization.Serializable

@Serializable
internal data class ProductRequestDTO(
    val id: Long = 0,
    val name: String,
    val categories: List<CategoryResponseDTO>,
    val price: Double,
    val quantity: Int
)
