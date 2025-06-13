package br.com.conding.tv.features.settings.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import br.com.conding.tv.components.model.DefinitionsScreen
import br.com.conding.tv.components.ui.ContentScreen
import br.com.conding.tv.features.settings.ui.viewmodel.SettingViewModel
import br.com.conding.tv.networking.resources.HttpError
import br.com.conding.tv.resources.GenericsStrings
import org.koin.mp.KoinPlatform.getKoin

@Composable
internal fun EditProfileUserScreen(
    goToBackScreen: () -> Unit = {},
    goToAlternativeRoutes: (HttpError) -> Unit = {},
    onSuccess: () -> Unit = {}
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
            UiResponseGetUserAuthenticated(
                viewModel = viewModel,
                onSuccess = {
                    MobileEditProfileUserScreen(
                        viewModel = viewModel,
                        userResponseVO = it,
                        goToAlternativeRoutes = goToAlternativeRoutes,
                        onSuccess = onSuccess
                    )
                }
            )
        }
    )
}
