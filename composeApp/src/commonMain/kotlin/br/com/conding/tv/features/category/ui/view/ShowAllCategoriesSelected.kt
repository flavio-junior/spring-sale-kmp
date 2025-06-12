package br.com.conding.tv.features.category.ui.view

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.conding.tv.components.ui.Description
import br.com.conding.tv.features.category.data.dto.CategoryResponseDTO
import br.com.conding.tv.theme.Themes

@Composable
internal fun ShowAllCategoriesSelected(
    selectedCategories: List<CategoryResponseDTO>
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = Themes.size.spaceSize8),
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(state = rememberScrollState())
    ) {
        selectedCategories.forEach {
            Description(label = it.name, backgroundTransparent = true)
        }
    }
}
