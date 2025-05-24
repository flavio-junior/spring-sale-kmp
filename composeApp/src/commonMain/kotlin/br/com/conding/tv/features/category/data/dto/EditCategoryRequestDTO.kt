package br.com.conding.tv.features.category.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class EditCategoryRequestDTO(
    val id: Long,
    val name: String
)
