package br.com.conding.tv.features.product.data.datasource

import br.com.conding.tv.features.product.data.dto.ProductRequestDTO
import br.com.conding.tv.features.product.data.dto.ProductsResponseDTO
import br.com.conding.tv.features.product.data.dto.RestockProductRequestDTO
import br.com.conding.tv.features.product.data.dto.UpdatePriceProductRequestDTO
import br.com.conding.tv.features.product.data.dto.UpdateProductRequestDTO
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import kotlinx.coroutines.flow.Flow

internal interface ProductRemoteDataSource {
    fun findAllProducts(
        name: String,
        page: Int,
        size: Int,
        sort: String
    ): Flow<ObserveNetworkStateHandler<ProductsResponseDTO>>
    fun createNewProduct(product: ProductRequestDTO): Flow<ObserveNetworkStateHandler<Unit>>
    fun updateProduct(product: UpdateProductRequestDTO): Flow<ObserveNetworkStateHandler<Unit>>
    fun updatePriceProduct(id: Long, price: UpdatePriceProductRequestDTO): Flow<ObserveNetworkStateHandler<Unit>>
    fun restockProduct(id: Long, stock: RestockProductRequestDTO): Flow<ObserveNetworkStateHandler<Unit>>
    fun deleteProduct(id: Long): Flow<ObserveNetworkStateHandler<Unit>>
}
