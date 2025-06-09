package br.com.conding.tv.components.model

import br.com.conding.tv.navigation.AppDestinations
import br.com.conding.tv.resources.IconName

internal data class Menu(
    val icon: IconName,
    val label: String,
    val route: AppDestinations
)
