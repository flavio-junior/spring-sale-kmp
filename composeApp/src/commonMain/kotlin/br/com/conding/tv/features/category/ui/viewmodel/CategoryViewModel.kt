package br.com.conding.tv.features.category.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.conding.tv.components.ui.UiState
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class CategoryViewModel(
    private val repository: CategoryRepository,
    private val converter: ConverterCategory
) : ViewModel() {

    private var nameDefault: String = EMPTY_TEXT
    private var currentPage: Int = NUMBER_ZERO
    private var sizeDefault: Int = NUMBER_SIXTY
    private var sortDefault: String = ASC

    private val _findAllCategories = MutableStateFlow<UiState<CategoriesResponseVO>>(UiState.Init)
    val findAllCategories = _findAllCategories.asStateFlow()

    private val _findCategoryByName =
        MutableStateFlow<UiState<List<CategoryResponseDTO>?>>(UiState.Init)
    val findCategoryByName = _findCategoryByName.asStateFlow()

    private val _createNewCategory = MutableStateFlow<UiState<Unit>>(UiState.Init)
    val createNewCategory = _createNewCategory.asStateFlow()

    private val _updateCategory = MutableStateFlow<UiState<Unit>>(UiState.Init)
    val updateCategory = _updateCategory.asStateFlow()

    private val _deleteCategory = MutableStateFlow<UiState<Unit>>(UiState.Init)
    val deleteCategory = _deleteCategory.asStateFlow()

    fun findAllCategories(
        name: String = this.nameDefault,
        sort: String = this.sortDefault,
        size: Int = this.sizeDefault
    ) {
        this.nameDefault = name
        this.sortDefault = sort
        this.sizeDefault = size
        viewModelScope.launch {
            repository.findAllCategories(
                name = nameDefault,
                page = currentPage,
                size = sizeDefault,
                sort = sortDefault
            )
                .collect { response ->
                    when (response) {
                        is ObserveNetworkStateHandler.Loading ->
                            _findAllCategories.value = UiState.Loading

                        is ObserveNetworkStateHandler.Error ->
                            _findAllCategories.value = UiState.Error(error = response.exception)

                        is ObserveNetworkStateHandler.Success -> {
                            val converter =
                                converter.converterContentDTOToVO(content = response.result)
                            _findAllCategories.value = UiState.OnSuccess(response = converter)
                        }
                    }
                }
        }
    }

    fun loadNextPage() {
        val currentDataState = _findAllCategories.value
        if (currentDataState is UiState.OnSuccess) {
            val lastPage = currentDataState.response.totalPages ?: 0
            if (currentPage < lastPage - NUMBER_ONE) {
                currentPage++
                findAllCategories(
                    name = nameDefault,
                    sort = sortDefault,
                    size = sizeDefault
                )
            }
        }
    }

    fun reloadPreviousPage() {
        if (currentPage > NUMBER_ZERO) {
            currentPage--
            findAllCategories(
                name = nameDefault,
                sort = sortDefault,
                size = sizeDefault
            )
        }
    }

    fun reloadPage() {
        currentPage = NUMBER_ZERO
        findAllCategories(
            name = nameDefault,
            sort = sortDefault,
            size = sizeDefault
        )
    }

    fun findCategoryByName(name: String) {
        viewModelScope.launch {
            repository
                .finCategoryByName(name = CategoryNameRequestDTO(name = name)).collect { response ->
                    when (response) {
                        is ObserveNetworkStateHandler.Loading ->
                            _findCategoryByName.value = UiState.Loading

                        is ObserveNetworkStateHandler.Error ->
                            _findCategoryByName.value = UiState.Error(error = response.exception)

                        is ObserveNetworkStateHandler.Success -> {
                            _findCategoryByName.value =
                                UiState.OnSuccess(response = response.result)
                        }
                    }
                }
        }
    }

    fun createCategory(category: String) {
        viewModelScope.launch {
            repository.createNewCategory(category = CategoryRequestDTO(name = category))
                .collect { response ->
                    when (response) {
                        is ObserveNetworkStateHandler.Loading ->
                            _createNewCategory.value = UiState.Loading

                        is ObserveNetworkStateHandler.Error ->
                            _createNewCategory.value = UiState.Error(error = response.exception)

                        is ObserveNetworkStateHandler.Success ->
                            _createNewCategory.value = UiState.OnSuccess(response = Unit)
                    }
                }
        }
    }

    fun updateCategory(category: UpdateCategoryRequestDTO) {
        viewModelScope.launch {
            repository.updateCategory(category = category).collect { response ->
                when (response) {
                    is ObserveNetworkStateHandler.Loading ->
                        _updateCategory.value = UiState.Loading

                    is ObserveNetworkStateHandler.Error ->
                        _updateCategory.value = UiState.Error(error = response.exception)

                    is ObserveNetworkStateHandler.Success ->
                        _updateCategory.value = UiState.OnSuccess(response = Unit)
                }
            }
        }
    }

    fun deleteCategory(id: Long) {
        viewModelScope.launch {
            repository.deleteCategory(id = id).collect { response ->
                when (response) {
                    is ObserveNetworkStateHandler.Loading ->
                        _deleteCategory.value = UiState.Loading

                    is ObserveNetworkStateHandler.Error ->
                        _deleteCategory.value = UiState.Error(error = response.exception)

                    is ObserveNetworkStateHandler.Success ->
                        _deleteCategory.value = UiState.OnSuccess(response = Unit)
                }
            }
        }
    }

    fun resetCategory(reset: ResetCategory) {
        when (reset) {
            ResetCategory.FIND_ALL_CATEGORIES -> {
                _findAllCategories.value = UiState.Init
            }

            ResetCategory.FIND_CATEGORY_BY_NAME -> {
                _findCategoryByName.value = UiState.Init
            }

            ResetCategory.CREATE_CATEGORY -> {
                _createNewCategory.value = UiState.Init
                findAllCategories()
            }

            ResetCategory.UPDATE_CATEGORY -> {
                _updateCategory.value = UiState.Init
                findAllCategories()
            }

            ResetCategory.DELETE_CATEGORY -> {
                _deleteCategory.value = UiState.Init
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
