package br.com.conding.tv.features.settings.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.ImeAction
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.conding.tv.components.model.DefinitionsScreen
import br.com.conding.tv.components.ui.ContentScreen
import br.com.conding.tv.components.ui.LoadingButton
import br.com.conding.tv.components.ui.UiResponse
import br.com.conding.tv.components.ui.TextField
import br.com.conding.tv.components.ui.UiState
import br.com.conding.tv.features.settings.data.dto.UserRequestDTO
import br.com.conding.tv.features.settings.data.vo.UserResponseVO
import br.com.conding.tv.features.settings.ui.viewmodel.SettingViewModel
import br.com.conding.tv.resources.GenericsStrings
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT
import br.com.conding.tv.resources.GenericsStrings.NOT_BLANK_OR_EMPTY
import br.com.conding.tv.resources.IconName
import br.com.conding.tv.resources.isNotBlankAndEmpty
import org.koin.mp.KoinPlatform.getKoin

@Composable
internal fun EditProfileUserScreen(
    goToBackScreen: () -> Unit = {}
) {
    ContentScreen(
        definitionsScreen = DefinitionsScreen(
            alignment = Alignment.Top,
            label = GenericsStrings.DATA_USER
        ),
        goToBackScreen = goToBackScreen,
        content = {
            val viewModel: SettingViewModel = getKoin().get()
            LaunchedEffect(key1 = Unit) {
                viewModel.getUserAuthenticated()
            }
            val uiState by viewModel.getUserAuthenticated.collectAsStateWithLifecycle()
            UiResponse(
                state = uiState,
                onSuccess = {
                    CardProfileUser(
                        viewModel = viewModel,
                        userResponseVO = it
                    )
                }
            )
        }
    )
}

@Composable
internal fun CardProfileUser(
    viewModel: SettingViewModel,
    userResponseVO: UserResponseVO? = null
) {
    var name: String by remember {
        mutableStateOf(value = userResponseVO?.name ?: EMPTY_TEXT)
    }
    var surname: String by remember {
        mutableStateOf(value = userResponseVO?.surname ?: EMPTY_TEXT)
    }
    var username: String by remember {
        mutableStateOf(value = userResponseVO?.username ?: EMPTY_TEXT)
    }
    var observer: Triple<Boolean, Boolean, String?> by remember {
        mutableStateOf(value = Triple(first = false, second = false, third = EMPTY_TEXT))
    }
    val saveInfoUser = {
        if (name.isNotBlankAndEmpty() &&
            surname.isNotBlankAndEmpty() &&
            username.isNotBlankAndEmpty()
        ) {
            observer = Triple(first = true, second = false, third = EMPTY_TEXT)
            val changeInfoUserRequestDTO = UserRequestDTO(
                name = name,
                surname = surname,
                username = username
            )
            viewModel.changeInfoUser(userRequestDTO = changeInfoUserRequestDTO)
        } else {
            observer = Triple(first = false, second = true, third = NOT_BLANK_OR_EMPTY)
        }
    }
    TextField(
        label = GenericsStrings.NAME,
        value = name,
        iconName = IconName.EDIT,
        isError = observer.second,
        onValueChange = { name = it }
    )
    TextField(
        label = GenericsStrings.SURNAME,
        value = surname,
        iconName = IconName.EDIT,
        isError = observer.second,
        onValueChange = { surname = it }
    )
    TextField(
        label = GenericsStrings.USERNAME,
        value = username,
        iconName = IconName.EDIT,
        isError = observer.second,
        onValueChange = { username = it },
        imeAction = ImeAction.Go,
        onGo = saveInfoUser
    )

    TextField(
        label = GenericsStrings.EMAIL,
        value = userResponseVO?.email ?: EMPTY_TEXT,
        enabled = false,
        iconName = IconName.MAIL,
        isError = observer.second,
        message = observer.third ?: EMPTY_TEXT
    )
    LoadingButton(
        onClick = saveInfoUser,
        isEnabled = observer.first,
        label = if (
            userResponseVO?.name?.isNotBlankAndEmpty() == true &&
            userResponseVO.surname?.isNotBlankAndEmpty() == true &&
            userResponseVO.username?.isNotBlankAndEmpty() == true
        ) {
            GenericsStrings.ALTER_DATA_USER
        } else {
            GenericsStrings.SAVE_DATA_USER
        }
    )
    UiResponseChangeInfoUserScreen(
        viewModel = viewModel,
        onError = {
            observer = it
        }
    )
}

@Composable
private fun UiResponseChangeInfoUserScreen(
    viewModel: SettingViewModel,
    onError: (Triple<Boolean, Boolean, String?>) -> Unit = {}
) {
    val uiState: UiState<Unit> by viewModel.changeInfoUser.collectAsStateWithLifecycle()
    UiResponse(
        state = uiState,
        onError = onError,
        goToAlternativeRoutes = {},
        onSuccess = {
            viewModel.getUserAuthenticated()
        }
    )
}
