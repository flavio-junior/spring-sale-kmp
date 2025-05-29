package br.com.conding.tv.features.account.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.conding.tv.features.account.data.dto.EmailRequestDTO
import br.com.conding.tv.features.account.data.dto.PasswordRequestDTO
import br.com.conding.tv.features.account.data.dto.SignInRequestDTO
import br.com.conding.tv.features.account.data.dto.SignUpRequestDTO
import br.com.conding.tv.features.account.data.dto.TokenResponseDTO
import br.com.conding.tv.features.account.data.repository.AccountRepository
import br.com.conding.tv.features.account.data.vo.TokenResponseVO
import br.com.conding.tv.features.account.domain.converter.ConverterToken
import br.com.conding.tv.networking.resources.DescriptionError
import br.com.conding.tv.networking.resources.ObserveNetworkStateHandler
import br.com.conding.tv.networking.storage.LocalStorage
import br.com.conding.tv.resources.GenericsStrings.INVALID_EMAIL
import br.com.conding.tv.resources.validateEmail
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

    private val _confirmEmailAddress =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val confirmEmailAddress: State<ObserveNetworkStateHandler<Unit>> = _confirmEmailAddress

    private val _checkCodeAlreadyExists =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val checkCodeAlreadyExists: State<ObserveNetworkStateHandler<Unit>> = _checkCodeAlreadyExists

    private val _signUp =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))

    private val _checkRecoverPassword =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val checkRecoverPassword: State<ObserveNetworkStateHandler<Unit>> = _checkRecoverPassword

    private val _createNewPassword =
        mutableStateOf<ObserveNetworkStateHandler<Unit>>(ObserveNetworkStateHandler.Loading(l = false))
    val createNewPassword: State<ObserveNetworkStateHandler<Unit>> = _createNewPassword


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

    fun confirmEmailAddress(email: EmailRequestDTO) {
        if (validateEmail(email = email.email)) {
            viewModelScope.launch {
                repository.confirmEmailAddress(email = email)
                    .onStart {
                        _confirmEmailAddress.value = ObserveNetworkStateHandler.Loading(l = true)
                    }
                    .collect {
                        _confirmEmailAddress.value = it
                    }
            }
        } else {
            _confirmEmailAddress.value = ObserveNetworkStateHandler.Error(
                e = DescriptionError(
                    message = INVALID_EMAIL
                )
            )
        }
    }

    fun checkCodeAlreadyExists(code: String) {
        viewModelScope.launch {
            repository.checkCodeAlreadyExists(code = code)
                .onStart {
                    _checkCodeAlreadyExists.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _checkCodeAlreadyExists.value = it
                }
        }
    }

    fun signUp(signUpRequestDTO: SignUpRequestDTO) {
        viewModelScope.launch {
            repository.signUp(signUpRequestDTO = signUpRequestDTO)
                .onStart {
                    _signUp.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    val signIn = SignInRequestDTO(
                        email = signUpRequestDTO.email,
                        password = signUpRequestDTO.password
                    )
                    signIn(signInRequestDTO = signIn)
                }
        }
    }

    fun sendRecoverPassword(email: EmailRequestDTO) {
        if (validateEmail(email = email.email)) {
            viewModelScope.launch {
                repository.sendRecoverPassword(email = email)
                    .onStart {
                        _confirmEmailAddress.value = ObserveNetworkStateHandler.Loading(l = true)
                    }
                    .collect {
                        _confirmEmailAddress.value = it
                    }
            }
        } else {
            _confirmEmailAddress.value =
                ObserveNetworkStateHandler.Error(e = DescriptionError(message = INVALID_EMAIL))
        }
    }

    fun checkRecoverPassword(code: String) {
        viewModelScope.launch {
            repository.checkRecoverPassword(code = code)
                .onStart {
                    _checkRecoverPassword.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _checkRecoverPassword.value = it
                }
        }
    }

    fun createNewPassword(passwordRequestDTO: PasswordRequestDTO) {
        viewModelScope.launch {
            repository.createNewPassword(passwordRequestDTO = passwordRequestDTO)
                .onStart {
                    _createNewPassword.value = ObserveNetworkStateHandler.Loading(l = true)
                }
                .collect {
                    _createNewPassword.value = it
                    val signIn = SignInRequestDTO(
                        email = passwordRequestDTO.email,
                        password = passwordRequestDTO.password
                    )
                    signIn(signInRequestDTO = signIn)
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

    fun resetStateSignIn(resetAccount: ResetAccount) {
        when (resetAccount) {
            ResetAccount.SIGN_IN ->
                _signIn.value = ObserveNetworkStateHandler.Loading(l = false)

            ResetAccount.CONFIRM_EMAIL_ADDRESS ->
                _confirmEmailAddress.value = ObserveNetworkStateHandler.Loading(l = false)

            ResetAccount.CHECK_CODE_ALREADY_EXISTS ->
                _checkCodeAlreadyExists.value = ObserveNetworkStateHandler.Loading(l = false)

            ResetAccount.SIGN_UP ->
                _signUp.value = ObserveNetworkStateHandler.Loading(l = false)

            ResetAccount.SEND_RECOVER_PASSWORD ->
                _confirmEmailAddress.value = ObserveNetworkStateHandler.Loading(l = false)

            ResetAccount.CHECK_RECOVER_PASSWORD ->
                _checkRecoverPassword.value = ObserveNetworkStateHandler.Loading(l = false)

            ResetAccount.CREATE_NEW_PASSWORD ->
                _createNewPassword.value = ObserveNetworkStateHandler.Loading(l = false)
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
