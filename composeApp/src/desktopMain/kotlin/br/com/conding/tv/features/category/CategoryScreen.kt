package br.com.conding.tv.features.category

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.conding.tv.domain.factory.availableServices
import br.com.conding.tv.features.category.data.vo.CategoryResponseVO
import br.com.conding.tv.features.shared.BodyPage
import br.com.conding.tv.features.shared.Services
import br.com.conding.tv.networking.resources.AlternativesRoutes
import br.com.conding.tv.resources.GenericsStrings.CATEGORIES
import br.com.conding.tv.resources.WeightSize.WEIGHT_SIZE
import br.com.conding.tv.resources.WeightSize.WEIGHT_SIZE_2
import br.com.conding.tv.theme.Themes
import br.com.conding.tv.utils.TypeLayout

@Composable
fun CategoryScreen(
    goToBackScreen: () -> Unit = {},
    goToNextScreen: (String) -> Unit = {},
    goToAlternativeRoutes: (AlternativesRoutes?) -> Unit = {}
) {
    var categoryVO: CategoryResponseVO by remember { mutableStateOf(value = CategoryResponseVO()) }
    BodyPage(
        typeLayout = TypeLayout.ROW,
        body = {
            Row {
                Services(
                    label = CATEGORIES,
                    options = availableServices,
                    goToBackScreen = goToBackScreen,
                    goToNextScreen = goToNextScreen
                )
                CardCategories(
                    modifier = Modifier
                        .weight(weight = WEIGHT_SIZE_2),
                    onItemSelected = { categoryVO = it },
                    goToAlternativeRoutes = goToAlternativeRoutes
                )
                EditCategory(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = Themes.size.spaceSize16, end = Themes.size.spaceSize16)
                        .weight(weight = WEIGHT_SIZE),
                    categoryVO = categoryVO,
                    goToAlternativeRoutes = goToAlternativeRoutes,
                    onCleanCategory = {
                        categoryVO = CategoryResponseVO()
                    }
                )
            }
        }
    )
}
