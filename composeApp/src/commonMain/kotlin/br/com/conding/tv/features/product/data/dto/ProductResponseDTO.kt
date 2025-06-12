package br.com.conding.tv.features.product.data.dto

import br.com.conding.tv.features.category.data.dto.CategoryResponseDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ProductResponseDTO(
    val id: Long = 0,
    @SerialName(value = "created_at")
    val createdAt: String = "",
    val name: String = "",
    val categories: List<CategoryResponseDTO> ? = null,
    val price: Double = 0.0,
    val quantity: Int = 0
)
