package br.com.conding.tv

import androidx.compose.ui.window.ComposeUIViewController
import br.com.conding.tv.features.main.ui.MainScreen
import platform.UIKit.UIViewController

val mainViewController: UIViewController
    get() = ComposeUIViewController { MainScreen() }