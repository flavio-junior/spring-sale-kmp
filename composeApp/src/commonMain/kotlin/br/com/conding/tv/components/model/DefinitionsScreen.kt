package br.com.conding.tv.components.model

import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically

data class DefinitionsScreen(
    val alignment: Alignment.Vertical = CenterVertically,
    val horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    val scroll: Boolean = true,
    val label: String? = null
)
