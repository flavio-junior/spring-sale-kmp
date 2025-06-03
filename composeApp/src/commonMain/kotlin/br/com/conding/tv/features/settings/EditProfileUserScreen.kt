package br.com.conding.tv.features.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import br.com.conding.tv.components.model.DefinitionsScreen
import br.com.conding.tv.components.ui.ContentScreen
import br.com.conding.tv.components.ui.Title
import br.com.conding.tv.resources.GenericsStrings.CREATE_MY_ACCOUNT

@Composable
fun EditProfileUserScreen(
    goToBackScreen: () -> Unit = {}
) {
    ContentScreen(
        definitionsScreen = DefinitionsScreen(
            alignment = Alignment.Top,
            label = CREATE_MY_ACCOUNT
        ),
        goToBackScreen = goToBackScreen,
        content = {
            Title(label = "Edit Profile User")
        }
    )
}
