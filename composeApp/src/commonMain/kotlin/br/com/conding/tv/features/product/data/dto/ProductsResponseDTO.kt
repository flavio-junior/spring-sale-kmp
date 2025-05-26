package br.com.conding.tv.features.product.data.dto

import br.com.conding.tv.features.others.dto.PageableDTO
import kotlinx.serialization.Serializable

@Serializable
data class ProductsResponseDTO(
    val totalPages: Int,
    val content: List<ProductResponseDTO>,
    val pageable: PageableDTO
)
