package br.com.conding.tv.features.settings.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.conding.tv.features.settings.data.dto.UserRequestDTO
import br.com.conding.tv.features.settings.data.repository.SettingsRepository
import br.com.conding.tv.features.settings.data.vo.UserResponseVO
import br.com.conding.tv.features.settings.domain.ConverterSettings
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class SettingViewModel(
    private val settingsRepository: SettingsRepository,
    private val converter: ConverterSettings
) : ViewModel(), SettingViewModelImpl {

    private val _getUserAuthenticated =
        mutableStateOf<ObserveNetworkStateHandler<UserResponseVO>>(
            ObserveNetworkStateHandler.Loading(l = false)
        )
    val getUserAuthenticated: State<ObserveNetworkStateHandler<UserResponseVO>> =
        _getUserAuthenticated

    private val _changeInfoUser =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(
            ObserveNetworkStateHandler.Loading(l = false)
        )
    val changeInfoUser: State<ObserveNetworkStateHandler<Unit>> = _changeInfoUser

    override fun getUserAuthenticated() {
        viewModelScope.launch {
            settingsRepository.getUserAuthenticated()
                .onStart {
                    _getUserAuthenticated.value = ObserveNetworkStateHandler.Loading(l = true)
                }.collect {
                    val result = converter.converterUserResponseDTOToVO(it.result)
                    _getUserAuthenticated.value = ObserveNetworkStateHandler.Success(s = result)
                }
        }
    }

    override fun changeInfoUser(userRequestDTO: UserRequestDTO) {
        viewModelScope.launch {
            viewModelScope.launch {
                settingsRepository.changeInfoUser(userRequestDTO = userRequestDTO)
                    .onStart {
                        _changeInfoUser.value = ObserveNetworkStateHandler.Loading(l = true)
                    }.collect {
                        _changeInfoUser.value = it
                    }
            }
        }
    }
}
