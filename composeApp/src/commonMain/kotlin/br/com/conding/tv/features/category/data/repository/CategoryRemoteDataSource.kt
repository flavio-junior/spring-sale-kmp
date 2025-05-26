package br.com.conding.tv.features.category.data.repository

import br.com.conding.tv.features.account.data.repository.LocalStorageImp
import br.com.conding.tv.features.category.data.dto.CategoriesResponseDTO
import br.com.conding.tv.features.category.data.dto.CategoryNameRequestDTO
import br.com.conding.tv.features.category.data.dto.CategoryRequestDTO
import br.com.conding.tv.features.category.data.dto.CategoryResponseDTO
import br.com.conding.tv.features.category.data.dto.EditCategoryRequestDTO
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import br.com.conding.tv.networking.resources.toResultFlow
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import io.ktor.http.path
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

class CategoryRemoteDataSource(
    private val httpClient: HttpClient,
    private val localStorage: LocalStorageImp
) : CategoryRepository {

    private val accessToken = runBlocking {
        localStorage.getToken().accessToken
    }

    override fun findAllCategories(
        name: String,
        page: Int,
        size: Int,
        sort: String
    ): Flow<ObserveNetworkStateHandler<CategoriesResponseDTO>> {
        return toResultFlow {
            httpClient.get {
                url {
                    path(path = arrayOf("/api/spring/sale/categories/v1"))
                    parameters.append(name = "name", name)
                    parameters.append(name = "page", page.toString())
                    parameters.append(name = "size", size.toString())
                    parameters.append(name = "sort", sort)
                }
                headers {
                    append(name = HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
            }
        }
    }

    override fun finCategoryByName(
        name: CategoryNameRequestDTO
    ): Flow<ObserveNetworkStateHandler<List<CategoryResponseDTO>>> {
        return toResultFlow {
            httpClient.get {
                url(urlString = "/api/spring/sale/categories/v1/category/${name.name}")
                headers {
                    append(name = HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
            }
        }
    }

    override fun createNewCategory(
        category: CategoryRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.post {
                url(urlString = "/api/spring/sale/categories/v1")
                headers {
                    append(name = HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
                setBody(body = category)
            }
        }
    }

    override fun editCategory(
        category: EditCategoryRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.put {
                url(urlString = "/api/spring/sale/categories/v1")
                headers {
                    append(name = HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
                setBody(body = category)
            }
        }
    }

    override fun deleteCategory(
        id: Long
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.delete {
                url(urlString = "/api/spring/sale/categories/v1/$id")
                headers {
                    append(name = HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
            }
        }
    }
}
