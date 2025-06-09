package br.com.conding.tv.features.category.data.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class UpdateCategoryRequestDTO(
    val id: Long? = null,
    val name: String
)
