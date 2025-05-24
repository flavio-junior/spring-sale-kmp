package br.com.conding.tv.features.account.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.conding.tv.features.account.data.dto.SignInRequestDTO
import br.com.conding.tv.features.account.data.dto.TokenResponseDTO
import br.com.conding.tv.features.account.data.repository.AccountRepository
import br.com.conding.tv.features.account.data.vo.TokenResponseVO
import br.com.conding.tv.features.account.domain.converter.ConverterToken
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import br.com.conding.tv.networking.storage.LocalStorage
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class AccountViewModel(
    private val localStorage: LocalStorage,
    private val repository: AccountRepository,
    private val converter: ConverterToken
) : ViewModel() {

    private val _signIn =
        mutableStateOf<ObserveNetworkStateHandler<TokenResponseDTO>>(
            ObserveNetworkStateHandler.Loading(
                l = false
            )
        )
    val signIn: State<ObserveNetworkStateHandler<TokenResponseDTO>> = _signIn

    private val _getTokenSaved =
        mutableStateOf<ObserveNetworkStateHandler<TokenResponseVO>>(
            ObserveNetworkStateHandler.Loading(
                l = false
            )
        )
    val getTokenSaved: State<ObserveNetworkStateHandler<TokenResponseVO>> = _getTokenSaved

    private val _cleanToken =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val cleanToken: State<ObserveNetworkStateHandler<Unit>> = _cleanToken

    fun signIn(signInRequestDTO: SignInRequestDTO) {
        viewModelScope.launch {
            repository.signIn(signIn = signInRequestDTO)
                .onStart {
                    _signIn.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _signIn.value = it
                    it.result?.let { result -> saveToken(token = result) }
                }
        }
    }

    private fun saveToken(token: TokenResponseDTO) {
        viewModelScope.launch {
            localStorage.saveToken(
                converter.converterTokenRequestDTOToTokenResponseVO(
                    token
                )
            )
        }
    }

    fun getToken() {
        viewModelScope.launch {
            _getTokenSaved.value = ObserveNetworkStateHandler.Loading(l = true)
            val token = localStorage.getToken()
            _getTokenSaved.value = ObserveNetworkStateHandler.Success(s = token)
        }
    }

    fun cleanToken() {
        viewModelScope.launch {
            _cleanToken.value = ObserveNetworkStateHandler.Loading(l = true)
            localStorage.cleanToken()
            _cleanToken.value = ObserveNetworkStateHandler.Success(s = Unit)
        }
    }

    fun resetStateSignIn() {
        _signIn.value = ObserveNetworkStateHandler.Loading(l = false)
    }
}
