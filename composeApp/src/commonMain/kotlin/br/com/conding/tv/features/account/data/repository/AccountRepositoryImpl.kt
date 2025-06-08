package br.com.conding.tv.features.account.data.repository

import br.com.conding.tv.features.account.data.datasource.remote.AccountRemoteDataSource
import br.com.conding.tv.features.account.data.dto.EmailRequestDTO
import br.com.conding.tv.features.account.data.dto.PasswordRequestDTO
import br.com.conding.tv.features.account.data.dto.SignInRequestDTO
import br.com.conding.tv.features.account.data.dto.SignUpRequestDTO
import br.com.conding.tv.features.account.data.dto.TokenResponseDTO
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import kotlinx.coroutines.flow.Flow

internal class AccountRepositoryImpl(
    private val accountRemoteDataSource: AccountRemoteDataSource
) : AccountRepository {

    override suspend fun signIn(
        signIn: SignInRequestDTO
    ): Flow<ObserveNetworkStateHandler<TokenResponseDTO>> {
        return accountRemoteDataSource.signIn(signIn = signIn)
    }

    override suspend fun confirmEmailAddress(
        email: EmailRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return accountRemoteDataSource.confirmEmailAddress(email = email)
    }

    override suspend fun checkCodeAlreadyExists(
        code: String
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return accountRemoteDataSource.checkCodeAlreadyExists(code = code)
    }

    override suspend fun signUp(
        signUpRequestDTO: SignUpRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return accountRemoteDataSource.signUp(signUpRequestDTO = signUpRequestDTO)
    }

    override suspend fun sendRecoverPassword(
        email: EmailRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return accountRemoteDataSource.sendRecoverPassword(email = email)
    }

    override suspend fun checkRecoverPassword(
        code: String
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return accountRemoteDataSource.checkRecoverPassword(code = code)
    }

    override suspend fun createNewPassword(
        passwordRequestDTO: PasswordRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return accountRemoteDataSource.createNewPassword(passwordRequestDTO = passwordRequestDTO)
    }

    override suspend fun refreshToken(
        email: String
    ): Flow<ObserveNetworkStateHandler<TokenResponseDTO>> {
        return accountRemoteDataSource.refreshToken(email = email)
    }
}
