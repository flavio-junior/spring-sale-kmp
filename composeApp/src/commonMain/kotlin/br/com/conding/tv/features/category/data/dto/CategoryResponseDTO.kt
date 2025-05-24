package br.com.conding.tv.features.category.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponseDTO(
    val id: Long = 0,
    val name: String = ""
)
