package br.com.conding.tv.features.product.data.repository

import br.com.conding.tv.features.account.data.repository.LocalStorageImp
import br.com.conding.tv.features.product.data.dto.ProductRequestDTO
import br.com.conding.tv.features.product.data.dto.ProductResponseDTO
import br.com.conding.tv.features.product.data.dto.ProductsResponseDTO
import br.com.conding.tv.features.product.data.dto.RestockProductRequestDTO
import br.com.conding.tv.features.product.data.dto.UpdatePriceProductRequestDTO
import br.com.conding.tv.features.product.data.dto.UpdateProductRequestDTO
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import br.com.conding.tv.networking.resources.toResultFlow
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import io.ktor.http.path
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

class ProductRemoteDataSource(
    private val httpClient: HttpClient,
    private val localStorage: LocalStorageImp
) : ProductRepository {

    private val accessToken = runBlocking {
        localStorage.getToken().accessToken
    }

    override fun findAllProducts(
        name: String,
        page: Int,
        size: Int,
        sort: String
    ): Flow<ObserveNetworkStateHandler<ProductsResponseDTO>> {
        return toResultFlow {
            httpClient.get {
                url {
                    path(path = arrayOf("/api/spring/sale/products/v1"))
                    parameters.append(name= "name", value = name)
                    parameters.append(name= "page", value = page.toString())
                    parameters.append(name= "size", value = size.toString())
                    parameters.append(name= "sort", value = sort)
                }
                headers {
                    append(name= HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
            }
        }
    }

    override fun finProductByName(
        name: String
    ): Flow<ObserveNetworkStateHandler<List<ProductResponseDTO>>> {
        return toResultFlow {
            httpClient.get {
                url {
                    path(path = arrayOf("/api/spring/sale/products/v1/find/product/by/$name"))
                }
                headers {
                    append(name= HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
            }
        }
    }

    override fun createNewProduct(
        product: ProductRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.post {
                url(urlString = "/api/spring/sale/products/v1")
                headers {
                    append(name= HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
                setBody(body = product)
            }
        }
    }

    override fun updateProduct(
        product: UpdateProductRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.put {
                url(urlString = "/api/spring/sale/products/v1")
                headers {
                    append(name= HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
                setBody(body = product)
            }
        }
    }

    override fun updatePriceProduct(
        id: Long,
        price: UpdatePriceProductRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.patch {
                url(urlString = "/api/spring/sale/products/v1/update/price/product/$id")
                headers {
                    append(name= HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
                setBody(body = price)
            }
        }
    }

    override fun restockProduct(
        id: Long,
        stock: RestockProductRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.patch {
                url(urlString = "/api/spring/sale/products/v1/restock/product/$id")
                headers {
                    append(name= HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
                setBody(body = stock)
            }
        }
    }

    override fun deleteProduct(
        id: Long
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.delete {
                url(urlString = "/api/spring/sale/products/v1/$id")
                headers {
                    append(name= HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
            }
        }
    }
}
