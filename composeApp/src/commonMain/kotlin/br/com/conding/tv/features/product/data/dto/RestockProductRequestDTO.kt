package br.com.conding.tv.features.product.data.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class RestockProductRequestDTO(
    val quantity: Int = 0
)
