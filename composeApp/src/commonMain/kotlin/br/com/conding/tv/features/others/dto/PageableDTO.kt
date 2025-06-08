package br.com.conding.tv.features.others.dto

import kotlinx.serialization.Serializable

@Serializable
data class PageableDTO(
    val pageNumber: Int? = 0
)
