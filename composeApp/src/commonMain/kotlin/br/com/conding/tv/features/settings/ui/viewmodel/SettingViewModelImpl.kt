package br.com.conding.tv.features.settings.ui.viewmodel

import br.com.conding.tv.features.settings.data.dto.UserRequestDTO

internal interface SettingViewModelImpl {
    fun getUserAuthenticated()
    fun changeInfoUser(userRequestDTO: UserRequestDTO)
    fun deleteMyAccount()
}
