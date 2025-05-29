package br.com.conding.tv.components.model

import br.com.conding.tv.resources.IconName

data class Menu(
    val icon: IconName,
    val label: String,
    val route: String
)
