package br.com.conding.tv.features.category.data.datasource

import br.com.conding.tv.features.category.data.api.CategoryApiService
import br.com.conding.tv.features.category.data.dto.CategoriesResponseDTO
import br.com.conding.tv.features.category.data.dto.CategoryNameRequestDTO
import br.com.conding.tv.features.category.data.dto.CategoryRequestDTO
import br.com.conding.tv.features.category.data.dto.CategoryResponseDTO
import br.com.conding.tv.features.category.data.dto.UpdateCategoryRequestDTO
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import kotlinx.coroutines.flow.Flow

internal class CategoryRemoteDataSourceImpl(
    private val categoryApiService: CategoryApiService
) : CategoryRemoteDataSource {

    override fun findAllCategories(
        name: String,
        page: Int,
        size: Int,
        sort: String
    ): Flow<ObserveNetworkStateHandler<CategoriesResponseDTO>> {
        return categoryApiService.findAllCategories(
            name = name,
            page = page,
            size = size,
            sort = sort
        )
    }

    override fun finCategoryByName(
        name: CategoryNameRequestDTO
    ): Flow<ObserveNetworkStateHandler<List<CategoryResponseDTO>>> {
        return categoryApiService.finCategoryByName(name = name)
    }

    override fun createNewCategory(
        category: CategoryRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return categoryApiService.createNewCategory(category = category)
    }

    override fun updateCategory(
        category: UpdateCategoryRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return categoryApiService.updateCategory(category = category)
    }

    override fun deleteCategory(
        id: Long
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return categoryApiService.deleteCategory(id = id)
    }
}
