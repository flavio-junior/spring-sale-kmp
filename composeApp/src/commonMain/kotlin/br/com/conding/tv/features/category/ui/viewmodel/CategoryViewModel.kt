package br.com.conding.tv.features.category.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.conding.tv.components.model.LocationRoute
import br.com.conding.tv.features.category.data.dto.CategoryNameRequestDTO
import br.com.conding.tv.features.category.data.dto.CategoryRequestDTO
import br.com.conding.tv.features.category.data.dto.CategoryResponseDTO
import br.com.conding.tv.features.category.data.dto.UpdateCategoryRequestDTO
import br.com.conding.tv.features.category.data.repository.CategoryRepository
import br.com.conding.tv.features.category.data.vo.CategoriesResponseVO
import br.com.conding.tv.features.category.domain.ConverterCategory
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT
import br.com.conding.tv.resources.Settings.ASC
import br.com.conding.tv.theme.NumbersUtils.NUMBER_ONE
import br.com.conding.tv.theme.NumbersUtils.NUMBER_SIXTY
import br.com.conding.tv.theme.NumbersUtils.NUMBER_ZERO
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val repository: CategoryRepository,
    private val converter: ConverterCategory
) : ViewModel() {

    private var currentPage: Int = NUMBER_ZERO
    private var sizeDefault: Int = NUMBER_SIXTY
    private var sort: String = ASC

    private val _findAllCategories =
        mutableStateOf<ObserveNetworkStateHandler<CategoriesResponseVO>>(
            ObserveNetworkStateHandler.Loading(l = false)
        )
    val findAllCategories: State<ObserveNetworkStateHandler<CategoriesResponseVO>> =
        _findAllCategories

    private val _findCategoryByName =
        mutableStateOf<ObserveNetworkStateHandler<List<CategoryResponseDTO>>>(
            ObserveNetworkStateHandler.Loading(l = false)
        )
    val findCategoryByName: State<ObserveNetworkStateHandler<List<CategoryResponseDTO>>> =
        _findCategoryByName

    private val _createNewCategory =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val createNewCategory: State<ObserveNetworkStateHandler<Unit>> = _createNewCategory

    private val _updateCategory =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val updateCategory: State<ObserveNetworkStateHandler<Unit>> = _updateCategory

    private val _deleteCategory =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val deleteCategory: State<ObserveNetworkStateHandler<Unit>> = _deleteCategory

    fun findAllCategories(
        name: String = EMPTY_TEXT,
        sort: String = this.sort,
        size: Int = this.sizeDefault,
        route: LocationRoute = LocationRoute.SEARCH
    ) {
        when (route) {
            LocationRoute.SEARCH, LocationRoute.SORT, LocationRoute.RELOAD -> {}
            LocationRoute.FILTER -> {
                this.currentPage = NUMBER_ZERO
            }
        }
        viewModelScope.launch {
            sizeDefault = size
            repository.findAllCategories(
                name = name,
                page = currentPage,
                size = sizeDefault,
                sort = sort
            )
                .onStart {
                    _findAllCategories.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    it.result?.let { response ->
                        _findAllCategories.value = ObserveNetworkStateHandler.Success(
                            s = converter.converterContentDTOToVO(content = response)
                        )
                    }
                }
        }
    }

    fun loadNextPage() {
        val lastPage = _findAllCategories.value.result?.totalPages ?: 0
        if (currentPage < lastPage - NUMBER_ONE) {
            currentPage++
            findAllCategories()
        }
    }

    fun reloadPreviousPage() {
        if (currentPage > NUMBER_ZERO) {
            currentPage--
            findAllCategories()
        }
    }

    fun findCategoryByName(name: String) {
        viewModelScope.launch {
            repository.finCategoryByName(name = CategoryNameRequestDTO(name = name))
                .onStart {
                    _findCategoryByName.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _findCategoryByName.value = it
                }
        }
    }

    fun createCategory(category: String) {
        viewModelScope.launch {
            repository.createNewCategory(category = CategoryRequestDTO(name = category))
                .onStart {
                    _createNewCategory.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _createNewCategory.value = it
                }
        }
    }

    fun updateCategory(category: UpdateCategoryRequestDTO) {
        viewModelScope.launch {
            repository.updateCategory(category = category)
                .onStart {
                    _updateCategory.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _updateCategory.value = it
                }
        }
    }

    fun deleteCategory(id: Long) {
        viewModelScope.launch {
            repository.deleteCategory(id = id)
                .onStart {
                    _deleteCategory.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _deleteCategory.value = it
                }
        }
    }

    fun resetCategory(reset: ResetCategory) {
        when (reset) {
            ResetCategory.FIND_ALL_CATEGORIES -> {
                _findAllCategories.value = ObserveNetworkStateHandler.Loading(l = false)
            }
            ResetCategory.FIND_CATEGORY_BY_NAME -> {
                _findCategoryByName.value = ObserveNetworkStateHandler.Loading(l = false)
            }
            ResetCategory.CREATE_CATEGORY -> {
                _createNewCategory.value = ObserveNetworkStateHandler.Loading(l = false)
                findAllCategories()
            }
            ResetCategory.UPDATE_CATEGORY -> {
                _updateCategory.value = ObserveNetworkStateHandler.Loading(l = false)
                findAllCategories()
            }
            ResetCategory.DELETE_CATEGORY -> {
                _deleteCategory.value = ObserveNetworkStateHandler.Loading(l = false)
                findAllCategories()
            }
        }
    }
}

enum class ResetCategory {
    FIND_ALL_CATEGORIES,
    FIND_CATEGORY_BY_NAME,
    CREATE_CATEGORY,
    UPDATE_CATEGORY,
    DELETE_CATEGORY
}
