package br.com.conding.tv.features.product.data.repository

import br.com.conding.tv.features.product.data.dto.ProductRequestDTO
import br.com.conding.tv.features.product.data.dto.ProductsResponseDTO
import br.com.conding.tv.features.product.data.dto.RestockProductRequestDTO
import br.com.conding.tv.features.product.data.dto.UpdatePriceProductRequestDTO
import br.com.conding.tv.features.product.data.dto.UpdateProductRequestDTO
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT
import br.com.conding.tv.theme.NumbersUtils.NUMBER_SIXTY
import br.com.conding.tv.theme.NumbersUtils.NUMBER_ZERO
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun findAllProducts(
        name: String = EMPTY_TEXT,
        page: Int = NUMBER_ZERO,
        size: Int = NUMBER_SIXTY,
        sort: String
    ): Flow<ObserveNetworkStateHandler<ProductsResponseDTO>>
    fun createNewProduct(product: ProductRequestDTO): Flow<ObserveNetworkStateHandler<Unit>>
    fun updateProduct(product: UpdateProductRequestDTO): Flow<ObserveNetworkStateHandler<Unit>>
    fun updatePriceProduct(id: Long, price: UpdatePriceProductRequestDTO): Flow<ObserveNetworkStateHandler<Unit>>
    fun restockProduct(id: Long, stock: RestockProductRequestDTO): Flow<ObserveNetworkStateHandler<Unit>>
    fun deleteProduct(id: Long): Flow<ObserveNetworkStateHandler<Unit>>
}
