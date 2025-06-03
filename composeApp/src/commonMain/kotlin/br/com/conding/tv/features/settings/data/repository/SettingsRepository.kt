package br.com.conding.tv.features.settings.data.repository

import br.com.conding.tv.features.settings.data.dto.UserRequestDTO
import br.com.conding.tv.features.settings.data.dto.UserResponseDTO
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun getUserAuthenticated(): Flow<ObserveNetworkStateHandler<UserResponseDTO>>
    suspend fun changeInfoUser(userRequestDTO: UserRequestDTO): Flow<ObserveNetworkStateHandler<Unit>>
}
