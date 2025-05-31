package br.com.conding.tv.features.category.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.conding.tv.components.settings.TypeSystem
import br.com.conding.tv.components.ui.Description
import br.com.conding.tv.components.ui.EmptyList
import br.com.conding.tv.features.category.data.vo.CategoriesResponseVO
import br.com.conding.tv.features.category.data.vo.CategoryResponseVO
import br.com.conding.tv.resources.GenericsStrings.EMPTY_LIST_CATEGORIES

@Composable
fun MobileListCategories(
    categoriesResponseVO: CategoriesResponseVO? = null,
    onItemSelected: (CategoryResponseVO) -> Unit
) {
    if (categoriesResponseVO?.content?.isNotEmpty() == true) {
        Column(modifier = Modifier.fillMaxHeight()) {
            categoriesResponseVO.content.forEach {
                Description(label = it.name)
            }
            PageIndicatorCategories(categoriesResponseVO = categoriesResponseVO)
        }
    } else {
        EmptyList(type = TypeSystem.MOBILE, title = EMPTY_LIST_CATEGORIES)
    }
}
