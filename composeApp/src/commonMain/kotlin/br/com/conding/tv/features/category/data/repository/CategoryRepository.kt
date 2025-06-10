package br.com.conding.tv.features.category.data.repository

import br.com.conding.tv.features.category.data.dto.CategoriesResponseDTO
import br.com.conding.tv.features.category.data.dto.CategoryNameRequestDTO
import br.com.conding.tv.features.category.data.dto.CategoryRequestDTO
import br.com.conding.tv.features.category.data.dto.CategoryResponseDTO
import br.com.conding.tv.features.category.data.dto.UpdateCategoryRequestDTO
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import kotlinx.coroutines.flow.Flow

internal interface CategoryRepository {
    fun findAllCategories(
        name: String,
        page: Int,
        size: Int,
        sort: String
    ): Flow<ObserveNetworkStateHandler<CategoriesResponseDTO>>

    fun finCategoryByName(
        name: CategoryNameRequestDTO
    ): Flow<ObserveNetworkStateHandler<List<CategoryResponseDTO>>>

    fun createNewCategory(category: CategoryRequestDTO): Flow<ObserveNetworkStateHandler<Unit>>
    fun updateCategory(category: UpdateCategoryRequestDTO): Flow<ObserveNetworkStateHandler<Unit>>
    fun deleteCategory(id: Long): Flow<ObserveNetworkStateHandler<Unit>>
}
