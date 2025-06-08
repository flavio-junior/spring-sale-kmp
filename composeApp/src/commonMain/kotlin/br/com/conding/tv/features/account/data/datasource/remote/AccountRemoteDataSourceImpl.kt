package br.com.conding.tv.features.account.data.datasource.remote

import br.com.conding.tv.features.account.data.api.AccountApiService
import br.com.conding.tv.features.account.data.dto.EmailRequestDTO
import br.com.conding.tv.features.account.data.dto.PasswordRequestDTO
import br.com.conding.tv.features.account.data.dto.SignInRequestDTO
import br.com.conding.tv.features.account.data.dto.SignUpRequestDTO
import br.com.conding.tv.features.account.data.dto.TokenResponseDTO
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import kotlinx.coroutines.flow.Flow

internal class AccountRemoteDataSourceImpl(
    private val accountApiService: AccountApiService
) : AccountRemoteDataSource {

    override suspend fun signIn(
        signIn: SignInRequestDTO
    ): Flow<ObserveNetworkStateHandler<TokenResponseDTO>> {
        return accountApiService.signIn(signIn = signIn)
    }

    override suspend fun confirmEmailAddress(
        email: EmailRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return accountApiService.confirmEmailAddress(email = email)
    }

    override suspend fun checkCodeAlreadyExists(
        code: String
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return accountApiService.checkCodeAlreadyExists(code = code)
    }

    override suspend fun signUp(
        signUpRequestDTO: SignUpRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return accountApiService.signUp(signUpRequestDTO = signUpRequestDTO)
    }

    override suspend fun sendRecoverPassword(
        email: EmailRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return accountApiService.sendRecoverPassword(email = email)
    }

    override suspend fun checkRecoverPassword(
        code: String
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return accountApiService.checkRecoverPassword(code = code)
    }

    override suspend fun createNewPassword(
        passwordRequestDTO: PasswordRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return accountApiService.createNewPassword(passwordRequestDTO = passwordRequestDTO)
    }

    override suspend fun refreshToken(
        email: String
    ): Flow<ObserveNetworkStateHandler<TokenResponseDTO>> {
        return accountApiService.refreshToken(email = email)
    }
}
