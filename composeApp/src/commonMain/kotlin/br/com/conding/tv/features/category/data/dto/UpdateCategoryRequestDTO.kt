package br.com.conding.tv.features.category.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdateCategoryRequestDTO(
    val id: Long? = null,
    val name: String
)
