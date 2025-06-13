package br.com.conding.tv.features.product.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.conding.tv.components.ui.UiState
import br.com.conding.tv.features.product.data.dto.ProductRequestDTO
import br.com.conding.tv.features.product.data.dto.RestockProductRequestDTO
import br.com.conding.tv.features.product.data.dto.UpdatePriceProductRequestDTO
import br.com.conding.tv.features.product.data.dto.UpdateProductRequestDTO
import br.com.conding.tv.features.product.data.repository.ProductRepository
import br.com.conding.tv.features.product.data.vo.ProductsResponseVO
import br.com.conding.tv.features.product.domain.ConverterProduct
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT
import br.com.conding.tv.resources.Settings.ASC
import br.com.conding.tv.theme.NumbersUtils.NUMBER_ONE
import br.com.conding.tv.theme.NumbersUtils.NUMBER_SIXTY
import br.com.conding.tv.theme.NumbersUtils.NUMBER_ZERO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class ProductViewModel(
    private val repository: ProductRepository,
    private val converter: ConverterProduct
) : ViewModel() {

    private var nameDefault: String = EMPTY_TEXT
    private var currentPage: Int = NUMBER_ZERO
    private var sizeDefault: Int = NUMBER_SIXTY
    private var sortDefault: String = ASC

    private val _findAllProducts = MutableStateFlow<UiState<ProductsResponseVO>>(UiState.Init)
    val findAllProducts = _findAllProducts.asStateFlow()

    var showEmptyList = mutableStateOf(value = true)

    private val _createProduct = MutableStateFlow<UiState<Unit>>(UiState.Init)
    val createProduct = _createProduct.asStateFlow()

    private val _updateProduct = MutableStateFlow<UiState<Unit>>(UiState.Init)
    val updateProduct = _updateProduct.asStateFlow()

    private val _updatePriceProduct = MutableStateFlow<UiState<Unit>>(UiState.Init)
    val updatePriceProduct = _updatePriceProduct.asStateFlow()

    private val _restockProduct = MutableStateFlow<UiState<Unit>>(UiState.Init)
    val restockProduct = _restockProduct.asStateFlow()

    private val _deleteProduct = MutableStateFlow<UiState<Unit>>(UiState.Init)
    val deleteProduct = _deleteProduct.asStateFlow()

    fun findAllProducts(
        name: String = this.nameDefault,
        sort: String = this.sortDefault,
        size: Int = this.sizeDefault
    ) {
        this.nameDefault = name
        this.sortDefault = sort
        this.sizeDefault = size
        viewModelScope.launch {
            repository.findAllProducts(
                name = nameDefault,
                page = currentPage,
                size = sizeDefault,
                sort = sortDefault
            )
                .collect { response ->
                    when (response) {
                        is ObserveNetworkStateHandler.Loading -> {
                            _findAllProducts.value = UiState.Loading
                        }

                        is ObserveNetworkStateHandler.Error -> {
                            _findAllProducts.value = UiState.Error(error = response.exception)
                        }

                        is ObserveNetworkStateHandler.Success -> {
                            val objectConverted =
                                converter.converterContentDTOToVO(content = response.result)
                            if (objectConverted.content?.isNotEmpty() == true) {
                                showEmptyList.value = false
                                _findAllProducts.value =
                                    UiState.OnSuccess(response = objectConverted)
                            } else {
                                _findAllProducts.value =
                                    UiState.OnSuccess(response = objectConverted)
                            }
                        }
                    }
                }
        }
    }

    fun showEmptyList(show: Boolean) {
        showEmptyList.value = show
    }

    fun loadNextPage() {
        val currentDataState = _findAllProducts.value
        if (currentDataState is UiState.OnSuccess) {
            val lastPage = currentDataState.response.totalPages ?: 0
            if (currentPage < lastPage - NUMBER_ONE) {
                currentPage++
                findAllProducts()
            }
        }
    }

    fun reloadPreviousPage() {
        if (currentPage > NUMBER_ZERO) {
            currentPage--
            findAllProducts()
        }
    }

    fun reloadPage() {
        currentPage = NUMBER_ZERO
        findAllProducts(
            name = nameDefault,
            sort = sortDefault,
            size = sizeDefault
        )
    }

    fun createProduct(product: ProductRequestDTO) {
        viewModelScope.launch {
            repository.createNewProduct(product = product).collect { response ->
                when (response) {
                    is ObserveNetworkStateHandler.Loading -> {
                        _createProduct.value = UiState.Loading
                    }

                    is ObserveNetworkStateHandler.Error -> {
                        _createProduct.value = UiState.Error(error = response.exception)
                    }

                    is ObserveNetworkStateHandler.Success -> {
                        _createProduct.value = UiState.OnSuccess(response = Unit)
                    }
                }
            }
        }
    }

    fun updateProduct(product: UpdateProductRequestDTO) {
        viewModelScope.launch {
            repository.updateProduct(product = product).collect { response ->
                when (response) {
                    is ObserveNetworkStateHandler.Loading -> {
                        _updateProduct.value = UiState.Loading
                    }

                    is ObserveNetworkStateHandler.Error -> {
                        _updateProduct.value = UiState.Error(error = response.exception)
                    }

                    is ObserveNetworkStateHandler.Success -> {
                        _updateProduct.value = UiState.OnSuccess(response = Unit)
                    }
                }
            }
        }
    }

    fun updatePriceProduct(id: Long, price: UpdatePriceProductRequestDTO) {
        viewModelScope.launch {
            repository.updatePriceProduct(id = id, price = price).collect { response ->
                when (response) {
                    is ObserveNetworkStateHandler.Loading -> {
                        _updatePriceProduct.value = UiState.Loading
                    }

                    is ObserveNetworkStateHandler.Error -> {
                        _updateProduct.value = UiState.Error(error = response.exception)
                    }

                    is ObserveNetworkStateHandler.Success -> {
                        _updatePriceProduct.value = UiState.OnSuccess(response = Unit)
                    }
                }
            }
        }
    }

    fun restockProduct(id: Long, stock: RestockProductRequestDTO) {
        viewModelScope.launch {
            repository.restockProduct(id = id, stock = stock).collect { response ->
                when (response) {
                    is ObserveNetworkStateHandler.Loading -> {
                        _restockProduct.value = UiState.Loading
                    }

                    is ObserveNetworkStateHandler.Error -> {
                        _restockProduct.value = UiState.Error(error = response.exception)
                    }

                    is ObserveNetworkStateHandler.Success -> {
                        _restockProduct.value = UiState.OnSuccess(response = Unit)
                    }
                }
            }
        }
    }

    fun deleteProduct(id: Long) {
        viewModelScope.launch {
            repository.deleteProduct(id = id).collect { response ->
                when (response) {
                    is ObserveNetworkStateHandler.Loading -> {
                        _deleteProduct.value = UiState.Loading
                    }

                    is ObserveNetworkStateHandler.Error -> {
                        _deleteProduct.value = UiState.Error(error = response.exception)
                    }

                    is ObserveNetworkStateHandler.Success -> {
                        _deleteProduct.value = UiState.OnSuccess(response = Unit)
                    }
                }
            }
        }
    }

    fun resetProduct(reset: ResetProduct) {
        when (reset) {
            ResetProduct.CREATE_PRODUCT -> _createProduct.value = UiState.Init
            ResetProduct.UPDATE_PRODUCT -> _updateProduct.value = UiState.Init
            ResetProduct.UPDATE_PRICE_PRODUCT -> _updatePriceProduct.value = UiState.Init
            ResetProduct.RESTOCK_PRODUCT -> _restockProduct.value = UiState.Init
            ResetProduct.DELETE_PRODUCT -> _deleteProduct.value = UiState.Init
        }
    }
}

enum class ResetProduct {
    CREATE_PRODUCT,
    UPDATE_PRODUCT,
    UPDATE_PRICE_PRODUCT,
    RESTOCK_PRODUCT,
    DELETE_PRODUCT
}
