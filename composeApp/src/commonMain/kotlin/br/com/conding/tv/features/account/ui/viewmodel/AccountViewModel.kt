package br.com.conding.tv.features.account.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.conding.tv.components.ui.UiState
import br.com.conding.tv.features.account.data.dto.EmailRequestDTO
import br.com.conding.tv.features.account.data.dto.PasswordRequestDTO
import br.com.conding.tv.features.account.data.dto.SignInRequestDTO
import br.com.conding.tv.features.account.data.dto.SignUpRequestDTO
import br.com.conding.tv.features.account.data.dto.TokenResponseDTO
import br.com.conding.tv.features.account.data.repository.AccountRepository
import br.com.conding.tv.features.account.data.datasource.local.LocalStorage
import br.com.conding.tv.features.account.data.vo.TokenResponseVO
import br.com.conding.tv.features.account.domain.converter.ConverterAccount
import br.com.conding.tv.networking.resources.DescriptionError
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import br.com.conding.tv.resources.GenericStrings.INVALID_EMAIL
import br.com.conding.tv.resources.validateEmail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class AccountViewModel(
    private val localStorage: LocalStorage,
    private val repository: AccountRepository,
    private val converter: ConverterAccount
) : ViewModel() {

    private val _signIn = MutableStateFlow<UiState<TokenResponseDTO>>(UiState.Init)
    val signIn = _signIn.asStateFlow()

    private val _confirmEmailAddress = MutableStateFlow<UiState<Unit>>(UiState.Init)
    val confirmEmailAddress = _confirmEmailAddress.asStateFlow()

    private val _checkCodeAlreadyExists = MutableStateFlow<UiState<Unit>>(UiState.Init)
    val checkCodeAlreadyExists = _checkCodeAlreadyExists.asStateFlow()

    private val _signUp = MutableStateFlow<UiState<Unit>>(UiState.Init)

    private val _checkRecoverPassword = MutableStateFlow<UiState<Unit>>(UiState.Init)
    val checkRecoverPassword = _checkRecoverPassword.asStateFlow()

    private val _createNewPassword = MutableStateFlow<UiState<Unit>>(UiState.Init)
    val createNewPassword = _createNewPassword.asStateFlow()

    private val _getTokenSaved = MutableStateFlow<UiState<TokenResponseVO>>(UiState.Init)
    val getTokenSaved = _getTokenSaved.asStateFlow()

    private val _cleanToken = MutableStateFlow<UiState<Unit>>(UiState.Init)
    val cleanToken = _cleanToken.asStateFlow()

    fun signIn(signInRequestDTO: SignInRequestDTO) {
        viewModelScope.launch {
            repository.signIn(signIn = signInRequestDTO).collect { response ->
                when (response) {
                    is ObserveNetworkStateHandler.Loading ->
                        _signIn.value = UiState.Loading

                    is ObserveNetworkStateHandler.Error ->
                        _signIn.value = UiState.Error(error = response.exception)

                    is ObserveNetworkStateHandler.Success -> {
                        if (response.result != null) {
                            saveToken(token = response.result)
                            _signIn.value = UiState.OnSuccess(response = response.result)
                        }
                    }
                }
            }
        }
    }

    fun confirmEmailAddress(email: EmailRequestDTO) {
        if (validateEmail(email = email.email)) {
            viewModelScope.launch {
                repository.confirmEmailAddress(email = email).collect { response ->
                    when (response) {
                        is ObserveNetworkStateHandler.Loading ->
                            _confirmEmailAddress.value = UiState.Loading

                        is ObserveNetworkStateHandler.Error ->
                            _confirmEmailAddress.value = UiState.Error(error = response.exception)

                        is ObserveNetworkStateHandler.Success ->
                            _confirmEmailAddress.value = UiState.OnSuccess(response = Unit)
                    }
                }
            }
        } else {
            _confirmEmailAddress.value =
                UiState.Error(error = DescriptionError(message = INVALID_EMAIL))
        }
    }

    fun checkCodeAlreadyExists(code: String) {
        viewModelScope.launch {
            repository.checkCodeAlreadyExists(code = code).collect { response ->
                when (response) {
                    is ObserveNetworkStateHandler.Loading ->
                        _checkCodeAlreadyExists.value = UiState.Loading

                    is ObserveNetworkStateHandler.Error ->
                        _checkCodeAlreadyExists.value = UiState.Error(error = response.exception)

                    is ObserveNetworkStateHandler.Success ->
                        _checkCodeAlreadyExists.value = UiState.OnSuccess(response = Unit)
                }
            }
        }
    }

    fun signUp(signUpRequestDTO: SignUpRequestDTO) {
        viewModelScope.launch {
            repository.signUp(signUpRequestDTO = signUpRequestDTO).collect { response ->
                when (response) {
                    is ObserveNetworkStateHandler.Loading ->
                        _signUp.value = UiState.Loading

                    is ObserveNetworkStateHandler.Error ->
                        _signUp.value = UiState.Error(error = response.exception)

                    is ObserveNetworkStateHandler.Success -> {
                        val signIn = SignInRequestDTO(
                            email = signUpRequestDTO.email,
                            password = signUpRequestDTO.password
                        )
                        signIn(signInRequestDTO = signIn)
                    }
                }
            }
        }
    }

    fun sendRecoverPassword(email: EmailRequestDTO) {
        if (validateEmail(email = email.email)) {
            viewModelScope.launch {
                repository.sendRecoverPassword(email = email).collect { response ->
                    when (response) {
                        is ObserveNetworkStateHandler.Loading ->
                            _confirmEmailAddress.value = UiState.Loading

                        is ObserveNetworkStateHandler.Error ->
                            _confirmEmailAddress.value = UiState.Error(error = response.exception)

                        is ObserveNetworkStateHandler.Success ->
                            _confirmEmailAddress.value = UiState.OnSuccess(response = Unit)
                    }
                }
            }
        } else {
            _confirmEmailAddress.value =
                UiState.Error(error = DescriptionError(message = INVALID_EMAIL))
        }
    }

    fun checkRecoverPassword(code: String) {
        viewModelScope.launch {
            repository.checkRecoverPassword(code = code).collect { response ->
                when (response) {
                    is ObserveNetworkStateHandler.Loading ->
                        _checkRecoverPassword.value = UiState.Loading

                    is ObserveNetworkStateHandler.Error ->
                        _checkRecoverPassword.value = UiState.Error(error = response.exception)

                    is ObserveNetworkStateHandler.Success ->
                        _checkRecoverPassword.value = UiState.OnSuccess(response = Unit)

                }
            }
        }
    }

    fun createNewPassword(passwordRequestDTO: PasswordRequestDTO) {
        viewModelScope.launch {
            repository.createNewPassword(passwordRequestDTO = passwordRequestDTO)
                .collect { response ->
                    when (response) {
                        is ObserveNetworkStateHandler.Loading ->
                            _createNewPassword.value = UiState.Loading

                        is ObserveNetworkStateHandler.Error ->
                            _createNewPassword.value = UiState.Error(error = response.exception)

                        is ObserveNetworkStateHandler.Success -> {
                            _createNewPassword.value = UiState.OnSuccess(response = Unit)
                            val signIn = SignInRequestDTO(
                                email = passwordRequestDTO.email,
                                password = passwordRequestDTO.password
                            )
                            signIn(signInRequestDTO = signIn)
                        }
                    }
                }
        }
    }

    private fun saveToken(token: TokenResponseDTO) {
        viewModelScope.launch {
            localStorage.saveToken(
                converter.converterTokenRequestDTOToTokenResponseVO(
                    tokenResponseDTO = token
                )
            )
        }
    }

    fun getToken() {
        viewModelScope.launch {
            _getTokenSaved.value = UiState.Loading
            val token = localStorage.getToken()
            _getTokenSaved.value = UiState.OnSuccess(response = token)
        }
    }

    fun cleanToken() {
        viewModelScope.launch {
            _cleanToken.value = UiState.Loading
            localStorage.cleanToken()
            _cleanToken.value = UiState.OnSuccess(response = Unit)
        }
    }

    fun resetStateSignIn(resetAccount: ResetAccount) {
        when (resetAccount) {
            ResetAccount.SIGN_IN ->
                _signIn.value = UiState.Init

            ResetAccount.CONFIRM_EMAIL_ADDRESS ->
                _confirmEmailAddress.value = UiState.Init

            ResetAccount.CHECK_CODE_ALREADY_EXISTS ->
                _checkCodeAlreadyExists.value = UiState.Init

            ResetAccount.SIGN_UP ->
                _signUp.value = UiState.Init

            ResetAccount.SEND_RECOVER_PASSWORD ->
                _confirmEmailAddress.value = UiState.Init

            ResetAccount.CHECK_RECOVER_PASSWORD ->
                _checkRecoverPassword.value = UiState.Init

            ResetAccount.CREATE_NEW_PASSWORD ->
                _createNewPassword.value = UiState.Init
        }
    }
}

enum class ResetAccount {
    SIGN_IN,
    CONFIRM_EMAIL_ADDRESS,
    CHECK_CODE_ALREADY_EXISTS,
    SIGN_UP,
    SEND_RECOVER_PASSWORD,
    CHECK_RECOVER_PASSWORD,
    CREATE_NEW_PASSWORD
}
