package br.com.conding.tv.features.settings.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.conding.tv.components.model.DefinitionsScreen
import br.com.conding.tv.components.ui.ContentScreen
import br.com.conding.tv.components.ui.UiResponse
import br.com.conding.tv.components.ui.UiState
import br.com.conding.tv.features.settings.ui.viewmodel.SettingViewModel
import br.com.conding.tv.resources.GenericsStrings
import org.koin.mp.KoinPlatform.getKoin

@Composable
internal fun EditProfileUserScreen(
    goToBackScreen: () -> Unit = {}
) {
    ContentScreen(
        definitionsScreen = DefinitionsScreen(
            label = GenericsStrings.DATA_USER,
            scroll = true
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
                    MobileEditProfileUserScreen(
                        viewModel = viewModel,
                        userResponseVO = it
                    )
                }
            )
        }
    )
}

@Composable
internal fun UiResponseChangeInfoUserScreen(
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
