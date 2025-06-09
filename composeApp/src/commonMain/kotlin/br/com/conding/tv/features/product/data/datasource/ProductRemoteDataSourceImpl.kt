package br.com.conding.tv.features.product.data.datasource

import br.com.conding.tv.features.product.data.api.ProductApiService
import br.com.conding.tv.features.product.data.dto.ProductRequestDTO
import br.com.conding.tv.features.product.data.dto.ProductsResponseDTO
import br.com.conding.tv.features.product.data.dto.RestockProductRequestDTO
import br.com.conding.tv.features.product.data.dto.UpdatePriceProductRequestDTO
import br.com.conding.tv.features.product.data.dto.UpdateProductRequestDTO
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import kotlinx.coroutines.flow.Flow

internal class ProductRemoteDataSourceImpl(
    private val productApiService: ProductApiService,
) : ProductRemoteDataSource {

    override fun findAllProducts(
        name: String,
        page: Int,
        size: Int,
        sort: String
    ): Flow<ObserveNetworkStateHandler<ProductsResponseDTO>> {
        return productApiService.findAllProducts(
            name = name,
            page = page,
            size = size,
            sort = sort
        )
    }

    override fun createNewProduct(
        product: ProductRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return productApiService.createNewProduct(product = product)
    }

    override fun updateProduct(
        product: UpdateProductRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return productApiService.updateProduct(product = product)
    }

    override fun updatePriceProduct(
        id: Long,
        price: UpdatePriceProductRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return productApiService.updatePriceProduct(id = id, price = price)
    }

    override fun restockProduct(
        id: Long,
        stock: RestockProductRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return productApiService.restockProduct(id = id, stock = stock)
    }

    override fun deleteProduct(
        id: Long
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return productApiService.deleteProduct(id = id)
    }
}