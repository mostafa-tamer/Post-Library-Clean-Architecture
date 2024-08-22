package com.mostafatamer.postlibrary.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mostafatamer.postlibrary.R
import com.mostafatamer.postlibrary.presentation.screens.navigatoin.FavoritePostsDestination
import com.mostafatamer.postlibrary.presentation.screens.navigatoin.PostDestination

data class BarItem(
    val title: String,
    val route: Any,
    val drawable: Int,
)

object NavBarItems {
    val items = listOf(
        BarItem(
            title = "Posts",
            route = PostDestination,
            drawable = R.drawable.post,
        ),
        BarItem(
            title = "Favorite",
            route = FavoritePostsDestination,
            drawable = R.drawable.favorite,
        ),
    )
}

@Composable
fun BottomNavigationBar(
    navHostController: NavHostController,
    navbarItems: List<BarItem> = NavBarItems.items,
) {
    val backStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRout = backStackEntry?.destination?.route



    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primary
    ) {
        navbarItems.forEach { navItem ->
            val rout = navItem.route.toString().substringBefore('@')

            BottomNavigationItem(
                modifier = Modifier.padding(vertical = 8.dp),
                selectedContentColor = MaterialTheme.colorScheme.onPrimary,
                selected = currentRout == rout,
                onClick = {
                    navHostController.navigate(rout) {
                        popUpTo(navHostController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = navItem.drawable),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                label = {
                    Text(
                        text = navItem.title,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            )
        }
    }
}