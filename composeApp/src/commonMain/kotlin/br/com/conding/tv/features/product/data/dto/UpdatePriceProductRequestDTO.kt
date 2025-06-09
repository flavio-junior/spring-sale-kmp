package br.com.conding.tv.features.product.data.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class UpdatePriceProductRequestDTO(
    val price: Double = 0.0,
)
