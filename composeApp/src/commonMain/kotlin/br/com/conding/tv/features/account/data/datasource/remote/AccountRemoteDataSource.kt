package br.com.conding.tv.features.account.data.datasource.remote

import br.com.conding.tv.features.account.data.dto.EmailRequestDTO
import br.com.conding.tv.features.account.data.dto.PasswordRequestDTO
import br.com.conding.tv.features.account.data.dto.SignInRequestDTO
import br.com.conding.tv.features.account.data.dto.SignUpRequestDTO
import br.com.conding.tv.features.account.data.dto.TokenResponseDTO
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import kotlinx.coroutines.flow.Flow

internal interface AccountRemoteDataSource {
    suspend fun signIn(signIn: SignInRequestDTO): Flow<ObserveNetworkStateHandler<TokenResponseDTO>>
    suspend fun confirmEmailAddress(email: EmailRequestDTO): Flow<ObserveNetworkStateHandler<Unit>>
    suspend fun checkCodeAlreadyExists(code: String): Flow<ObserveNetworkStateHandler<Unit>>
    suspend fun signUp(signUpRequestDTO: SignUpRequestDTO): Flow<ObserveNetworkStateHandler<Unit>>
    suspend fun sendRecoverPassword(email: EmailRequestDTO): Flow<ObserveNetworkStateHandler<Unit>>
    suspend fun checkRecoverPassword(code: String): Flow<ObserveNetworkStateHandler<Unit>>
    suspend fun createNewPassword(
        passwordRequestDTO: PasswordRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>>

    suspend fun refreshToken(email: String): Flow<ObserveNetworkStateHandler<TokenResponseDTO>>
}