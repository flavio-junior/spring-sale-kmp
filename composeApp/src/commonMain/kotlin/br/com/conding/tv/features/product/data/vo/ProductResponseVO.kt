package br.com.conding.tv.features.product.data.vo

import br.com.conding.tv.features.category.data.vo.CategoryResponseVO
import kotlinx.serialization.Serializable

@Serializable
internal data class ProductResponseVO(
    val id: Long = 0,
    val createdAt: String = "",
    val name: String = "",
    val categories: List<CategoryResponseVO>? = null,
    val price: Double = 0.0,
    val quantity: Int = 0
)
