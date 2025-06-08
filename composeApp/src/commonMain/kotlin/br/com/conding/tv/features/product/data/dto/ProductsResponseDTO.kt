package br.com.conding.tv.features.product.data.dto

import br.com.conding.tv.features.others.dto.PageableDTO
import kotlinx.serialization.Serializable

@Serializable
data class ProductsResponseDTO(
    val totalPages: Int? = 0,
    val content: List<ProductResponseDTO>? = null,
    val pageable: PageableDTO? = null
)
