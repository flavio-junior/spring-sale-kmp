package br.com.conding.tv.components.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import br.com.conding.tv.components.factory.homeBottomNavigation
import br.com.conding.tv.resources.getIconResource
import br.com.conding.tv.resources.onBorder
import br.com.conding.tv.theme.Themes

@Composable
internal fun BottomNavigationBar(
    navController: NavHostController
) {
    NavigationBar(
        containerColor = Themes.colors.background,
        modifier = Modifier
            .onBorder(
                onClick = { },
                spaceSize = Themes.size.spaceSize8,
                width = Themes.size.spaceSize2,
                color = Themes.colors.primary
            )
    ) {
        val navBackStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()
        val currentRoute: String? = navBackStackEntry?.destination?.route
        homeBottomNavigation.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = getIconResource(iconName = item.icon),
                        contentDescription = item.label,
                        tint = if (currentRoute == item.route.name)
                            Themes.colors.background else Themes.colors.primary
                    )
                },
                label = {
                    SimpleText(label = item.label, backgroundTransparent = true)
                },
                selected = currentRoute == item.route.name,
                onClick = {
                    navController.navigate(item.route.name) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = Themes.colors.primary,
                    selectedTextColor = Themes.colors.primary,
                    selectedIndicatorColor = Themes.colors.secondary,
                    unselectedIconColor = Themes.colors.primary,
                    unselectedTextColor = Themes.colors.primary,
                    disabledIconColor = Themes.colors.primary,
                    disabledTextColor = Themes.colors.primary
                )
            )
        }
    }
}
