package br.com.conding.tv.features.category.data.vo

import kotlinx.serialization.Serializable

@Serializable
internal data class CategoryResponseVO(
    val id: Long = 0,
    val name: String = ""
)
