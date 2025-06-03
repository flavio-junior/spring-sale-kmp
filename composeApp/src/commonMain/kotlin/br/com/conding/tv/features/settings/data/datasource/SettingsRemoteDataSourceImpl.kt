package br.com.conding.tv.features.settings.data.datasource

import br.com.conding.tv.features.settings.data.api.SettingsApiService
import br.com.conding.tv.features.settings.data.dto.UserRequestDTO
import br.com.conding.tv.features.settings.data.dto.UserResponseDTO
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import kotlinx.coroutines.flow.Flow

internal class SettingsRemoteDataSourceImpl(
    private val settingsApiService: SettingsApiService
) : SettingsRemoteDataSource {

    override suspend fun getUserAuthenticated(): Flow<ObserveNetworkStateHandler<UserResponseDTO>> {
        return settingsApiService.getUserAuthenticated()
    }

    override suspend fun changeInfoUser(
        userRequestDTO: UserRequestDTO
    ): Flow<ObserveNetworkStateHandler<Unit>> {
        return settingsApiService.changeInfoUser(userRequestDTO)
    }
}
