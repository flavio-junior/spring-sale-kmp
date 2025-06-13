package br.com.conding.tv.features.settings.data.repository

import br.com.conding.tv.features.settings.data.datasource.SettingsRemoteDataSource
import br.com.conding.tv.features.settings.data.dto.UserRequestDTO
import br.com.conding.tv.features.settings.data.dto.UserResponseDTO
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import kotlinx.coroutines.flow.Flow

internal class SettingsRepositoryImpl(
    private val settingsRemoteDataSource: SettingsRemoteDataSource
) : SettingsRepository {

    override suspend fun getUserAuthenticated(): Flow<ObserveNetworkStateHandler<UserResponseDTO>> {
        return settingsRemoteDataSource.getUserAuthenticated()
    }

    override suspend fun changeInfoUser(
        userRequestDTO: UserRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return settingsRemoteDataSource.changeInfoUser(userRequestDTO)
    }

    override suspend fun deleteMyAccount(): Flow<ObserveNetworkStateHandler<Unit>> {
        return settingsRemoteDataSource.deleteMyAccount()
    }
}
