package br.com.conding.tv.features.settings.data.api

import br.com.conding.tv.features.account.data.repository.LocalStorageImp
import br.com.conding.tv.features.settings.data.dto.UserRequestDTO
import br.com.conding.tv.features.settings.data.dto.UserResponseDTO
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import br.com.conding.tv.networking.resources.toResultFlow
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

internal class SettingsApiServiceImpl(
    private val httpClient: HttpClient,
    private val localStorage: LocalStorageImp
) : SettingsApiService {

    private val accessToken = runBlocking {
        localStorage.getToken().accessToken
    }

    override suspend fun getUserAuthenticated(): Flow<ObserveNetworkStateHandler<UserResponseDTO>> {
        return toResultFlow {
            httpClient.get {
                url(urlString = "/api/spring/sale/use/settings/v1")
                headers {
                    append(name = HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
            }
        }
    }

    override suspend fun changeInfoUser(
        userRequestDTO: UserRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.patch {
                url(urlString = "/api/spring/sale/use/settings/v1")
                headers {
                    append(name = HttpHeaders.Authorization, value = "Bearer $accessToken")
                }
                setBody(body = userRequestDTO)
            }
        }
    }
}
