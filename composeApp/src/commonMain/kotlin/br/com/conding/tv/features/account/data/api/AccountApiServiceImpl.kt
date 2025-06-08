package br.com.conding.tv.features.account.data.api

import br.com.conding.tv.features.account.data.dto.EmailRequestDTO
import br.com.conding.tv.features.account.data.dto.PasswordRequestDTO
import br.com.conding.tv.features.account.data.dto.SignInRequestDTO
import br.com.conding.tv.features.account.data.dto.SignUpRequestDTO
import br.com.conding.tv.features.account.data.dto.TokenResponseDTO
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import br.com.conding.tv.networking.resources.toResultFlow
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import kotlinx.coroutines.flow.Flow

class AccountApiServiceImpl(
    private val httpClient: HttpClient
) : AccountApiService {

    override suspend fun signIn(
        signIn: SignInRequestDTO
    ): Flow<ObserveNetworkStateHandler<TokenResponseDTO>> {
        return toResultFlow {
            httpClient.post(urlString = "/api/auth/v1/sign-in") {
                setBody(signIn)
            }
        }
    }

    override suspend fun confirmEmailAddress(
        email: EmailRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.post {
                url(urlString = "/api/auth/v1/confirm-email-address")
                setBody(body = email)
            }
        }
    }

    override suspend fun checkCodeAlreadyExists(
        code: String
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.get {
                url(urlString = "/api/auth/v1/check-code-verification/${code}")
            }
        }
    }

    override suspend fun signUp(
        signUpRequestDTO: SignUpRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.post {
                url(urlString = "/api/auth/v1/sign-up")
                setBody(body = signUpRequestDTO)
            }
        }
    }

    override suspend fun sendRecoverPassword(
        email: EmailRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.post {
                url(urlString = "/api/auth/v1/recover-password")
                setBody(body = email)
            }
        }
    }

    override suspend fun checkRecoverPassword(
        code: String
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.get {
                url(urlString = "/api/auth/v1/check-recover-password/${code}")
            }
        }
    }

    override suspend fun createNewPassword(
        passwordRequestDTO: PasswordRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return toResultFlow {
            httpClient.put(urlString = "/api/auth/v1/new-password") {
                setBody(body = passwordRequestDTO)
            }
        }
    }

    override suspend fun refreshToken(
        email: String
    ): Flow<ObserveNetworkStateHandler<TokenResponseDTO>> {
        return toResultFlow {
            httpClient.put(urlString = "/api/auth/v1/refresh-token/$email") {
                setBody(body = email)
            }
        }
    }
}
