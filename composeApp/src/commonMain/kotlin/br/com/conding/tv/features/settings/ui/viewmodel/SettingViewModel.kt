package br.com.conding.tv.features.settings.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.conding.tv.components.ui.UiState
import br.com.conding.tv.features.settings.data.dto.UserRequestDTO
import br.com.conding.tv.features.settings.data.dto.UserResponseDTO
import br.com.conding.tv.features.settings.data.repository.SettingsRepository
import br.com.conding.tv.features.settings.data.vo.UserResponseVO
import br.com.conding.tv.features.settings.domain.ConverterSettings
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class SettingViewModel(
    private val settingsRepository: SettingsRepository,
    private val converter: ConverterSettings
) : ViewModel(), SettingViewModelImpl {

    private val _getUserAuthenticated = MutableStateFlow<UiState<UserResponseVO>>(UiState.Init)
    val getUserAuthenticated = _getUserAuthenticated.asStateFlow()

    private val _changeInfoUser = MutableStateFlow<UiState<Unit>>(UiState.Init)
    val changeInfoUser = _changeInfoUser.asStateFlow()

    override fun getUserAuthenticated() {
        viewModelScope.launch {
            settingsRepository.getUserAuthenticated().collect { response ->
                when (response) {
                    is ObserveNetworkStateHandler.Loading -> _getUserAuthenticated.value =
                        UiState.Loading

                    is ObserveNetworkStateHandler.Error -> _getUserAuthenticated.value =
                        UiState.Error(
                            error = ObserveNetworkStateHandler.Error<UserResponseDTO>().exception
                        )

                    is ObserveNetworkStateHandler.Success -> _getUserAuthenticated.value =
                        UiState.OnSuccess(
                            response = converter.converterUserResponseDTOToVO(
                                userResponseDTO = response.result
                            )
                        )
                }
            }
        }
    }

    override fun changeInfoUser(userRequestDTO: UserRequestDTO) {
        viewModelScope.launch {
            settingsRepository.changeInfoUser(userRequestDTO = userRequestDTO).collect { response ->
                when (response) {
                    is ObserveNetworkStateHandler.Loading ->
                        _changeInfoUser.value = UiState.Loading

                    is ObserveNetworkStateHandler.Error -> {
                        _changeInfoUser.value = UiState.Error(error = response.exception)
                    }

                    is ObserveNetworkStateHandler.Success ->
                        _changeInfoUser.value = UiState.OnSuccess(response = Unit)
                }
            }
        }
    }
}
