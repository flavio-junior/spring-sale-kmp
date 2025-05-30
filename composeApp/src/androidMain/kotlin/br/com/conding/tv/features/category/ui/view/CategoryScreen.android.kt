package br.com.conding.tv.features.category.ui.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.conding.tv.components.model.DefinitionsScreen
import br.com.conding.tv.components.ui.ContentScreen
import br.com.conding.tv.components.ui.Search
import br.com.conding.tv.components.ui.SortBy
import br.com.conding.tv.components.ui.SubTitle
import br.com.conding.tv.components.ui.Title
import br.com.conding.tv.navigation.AppDestinations
import br.com.conding.tv.networking.resources.AlternativesRoutes
import br.com.conding.tv.resources.GenericsStrings
import br.com.conding.tv.resources.GenericsStrings.EMPTY_TEXT

@Composable
actual fun CategoriesScreen(
    goToBackScreen: () -> Unit,
    goToNextScreen: (AppDestinations) -> Unit,
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit
) {
    ContentScreen(
        definitionsScreen = DefinitionsScreen(
            alignment = Alignment.Top
        ),
        content = {
            var searchCategory by remember { mutableStateOf(value = EMPTY_TEXT) }
            Title(label = GenericsStrings.CATEGORIES, modifier = Modifier.fillMaxSize())
            Search(
                value = searchCategory,
                onValueChange = {
                    searchCategory = it
                }
            )
            Row {
                SubTitle(label = "Resultado")
                SortBy()
            }
        }
    )
}
