package br.com.conding.tv.features.settings.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import br.com.conding.tv.components.ui.ContentHorizontal
import br.com.conding.tv.components.ui.LoadingButton
import br.com.conding.tv.components.ui.TextField
import br.com.conding.tv.features.settings.data.dto.UserRequestDTO
import br.com.conding.tv.features.settings.ui.viewmodel.SettingViewModel
import br.com.conding.tv.networking.resources.HttpError
import br.com.conding.tv.resources.GenericsStrings
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT
import br.com.conding.tv.resources.GenericsStrings.NOT_BLANK_OR_EMPTY
import br.com.conding.tv.resources.IconName
import br.com.conding.tv.resources.WeightSize
import br.com.conding.tv.resources.isNotBlankAndEmpty
import br.com.conding.tv.theme.Themes
import org.koin.mp.KoinPlatform.getKoin

@Composable
internal fun DesktopEditProfileUserScreen(
    modifier: Modifier = Modifier,
    goToAlternativeRoutes: (HttpError) -> Unit = {},
    onSuccess: () -> Unit = {}
) {
    val viewModel: SettingViewModel = getKoin().get()
    LaunchedEffect(key1 = Unit) {
        viewModel.getUserAuthenticated()
    }
    UiResponseGetUserAuthenticated(
        viewModel = viewModel,
        onSuccess = { response ->
            var name: String by remember {
                mutableStateOf(value = response.name ?: EMPTY_TEXT)
            }
            var surname: String by remember {
                mutableStateOf(value = response.surname ?: EMPTY_TEXT)
            }
            var username: String by remember {
                mutableStateOf(value = response.username ?: EMPTY_TEXT)
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
            Column(
                modifier = Modifier.padding(
                    top = Themes.size.spaceSize16,
                    end = Themes.size.spaceSize16
                ),
                verticalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize16)
            ) {
                ContentHorizontal(spaceBy = Themes.size.spaceSize16) {
                    TextField(
                        label = GenericsStrings.NAME,
                        value = name,
                        iconName = IconName.EDIT,
                        isError = observer.second,
                        onValueChange = { name = it },
                        modifier = modifier.weight(weight = WeightSize.WEIGHT_SIZE)
                    )
                    TextField(
                        label = GenericsStrings.SURNAME,
                        value = surname,
                        iconName = IconName.EDIT,
                        isError = observer.second,
                        onValueChange = { surname = it },
                        modifier = modifier.weight(weight = WeightSize.WEIGHT_SIZE)
                    )
                }
                ContentHorizontal(spaceBy = Themes.size.spaceSize18) {
                    TextField(
                        label = GenericsStrings.USERNAME,
                        value = username,
                        iconName = IconName.EDIT,
                        isError = observer.second,
                        message = observer.third,
                        onValueChange = { username = it },
                        imeAction = ImeAction.Go,
                        onGo = saveInfoUser,
                        modifier = modifier.weight(weight = WeightSize.WEIGHT_SIZE)
                    )
                    TextField(
                        label = GenericsStrings.EMAIL,
                        value = response.email ?: EMPTY_TEXT,
                        enabled = false,
                        iconName = IconName.MAIL,
                        modifier = modifier.weight(weight = WeightSize.WEIGHT_SIZE)
                    )
                }
                ContentHorizontal(spaceBy = Themes.size.spaceSize18) {
                    LoadingButton(
                        onClick = saveInfoUser,
                        isEnabled = observer.first,
                        label = if (
                            response.name?.isNotBlankAndEmpty() == true &&
                            response.surname?.isNotBlankAndEmpty() == true &&
                            response.username?.isNotBlankAndEmpty() == true
                        ) {
                            GenericsStrings.ALTER_DATA_USER
                        } else {
                            GenericsStrings.SAVE_DATA_USER
                        },
                        modifier = modifier.weight(weight = WeightSize.WEIGHT_SIZE)
                    )
                    DesktopDeleteMyAccount(
                        goToAlternativeRoutes = goToAlternativeRoutes,
                        onSuccess = onSuccess,
                        modifier = modifier.weight(weight = WeightSize.WEIGHT_SIZE)
                    )
                }
                UiResponseChangeInfoUser(
                    viewModel = viewModel,
                    onError = {
                        observer = it
                    }
                )
            }
        }
    )
}
