package br.com.conding.tv

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import br.com.conding.tv.components.Description
import br.com.conding.tv.components.GenericsStrings.PROJECT_NAME
import br.com.conding.tv.components.SimpleText
import br.com.conding.tv.components.SubTitle
import br.com.conding.tv.components.Title
import br.com.conding.tv.theme.Themes
import org.jetbrains.compose.resources.painterResource
import springsale.composeapp.generated.resources.Res
import springsale.composeapp.generated.resources.campaign

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        icon = painterResource(resource = Res.drawable.campaign),
        title = PROJECT_NAME,
        state = rememberWindowState(placement = WindowPlacement.Maximized)
    ) {
        Column(modifier = Modifier
            .background(color = Themes.colors.background)
            .fillMaxSize()
        ) {
            Title(title = "Test",)
            SubTitle(subTitle = "Test", color = Themes.colors.primary)
            Description(description = "Test", color = Themes.colors.error)
            SimpleText(text = "Test", color = Themes.colors.success)
            Text(text = "Test", color = Themes.colors.secondary)
        }
    }
}