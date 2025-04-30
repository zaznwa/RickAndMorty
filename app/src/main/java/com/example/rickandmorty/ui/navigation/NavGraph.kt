package com.example.rickandmorty.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.rickandmorty.ui.screen.character.CharacterDetailScreen
import com.example.rickandmorty.ui.screen.character.CharactersScreen
import com.example.rickandmorty.ui.screen.episode.EpisodeDetailScreen
import com.example.rickandmorty.ui.screen.episode.EpisodesScreen
import com.example.rickandmorty.ui.screen.favorite.FavoriteScreen
import com.example.rickandmorty.ui.screen.location.LocationDetailScreen
import com.example.rickandmorty.ui.screen.location.LocationScreen

data class BottomNavItem(
    val screen: Screen,
    val icon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showAppBars = when {
        currentRoute?.startsWith(Screen.CharacterDetail.route) == true ||
                currentRoute?.startsWith(Screen.LocationDetail.route) == true ||
                currentRoute?.startsWith(Screen.EpisodeDetail.route) == true -> false

        else -> true
    }

    Scaffold(
        topBar = {
            if (showAppBars) {
                TopAppBar(
                    title = {
                        Text(
                            text = currentScreenTitle(currentRoute).toString(),
                            color = Color.White
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = DarkGray
                    )
                )
            }
        },
        bottomBar = {
            if (showAppBars) {
                BottomNavigationBar(navController, currentRoute)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Characters.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Characters.route) {
                CharactersScreen(navController)
            }
            composable(Screen.Locations.route) {
                LocationScreen(navController)
            }
            composable(Screen.Episodes.route) {
                EpisodesScreen(navController)
            }
            composable(Screen.Favorites.route) {
                FavoriteScreen(navController)
            }
            composable("${Screen.CharacterDetail.route}/{characterId}") { backStackEntry ->
                backStackEntry.arguments?.getString("characterId")?.let {
                    CharacterDetailScreen(
                        characterId = it,
                        navController = navController
                    )
                }
            }
            composable("${Screen.EpisodeDetail.route}/{episodeId}") { backStackEntry ->
                backStackEntry.arguments?.getString("episodeId")?.let {
                    EpisodeDetailScreen(
                        episodeId = it,
                        navController = navController
                    )
                }
            }
            composable("${Screen.LocationDetail.route}/{locationId}") { backStackEntry ->
                backStackEntry.arguments?.getString("locationId")?.let {
                    LocationDetailScreen(
                        locationId = it,
                        navController = navController
                    )
                }
            }
        }
    }
}


fun currentScreenTitle(currentRoute: String?): String? {
    return when {
        currentRoute == Screen.Characters.route -> Screen.Characters.title
        currentRoute == Screen.Locations.route -> Screen.Locations.title
        currentRoute == Screen.Episodes.route -> Screen.Episodes.title
        currentRoute == Screen.Favorites.route -> Screen.Favorites.title
        currentRoute?.startsWith(Screen.CharacterDetail.route) == true -> "Детали персонажа"
        currentRoute?.startsWith(Screen.LocationDetail.route) == true -> "Детали локации"
        currentRoute?.startsWith(Screen.EpisodeDetail.route) == true -> "Детали эпизода"
        else -> ""
    }
}

@Composable
fun BottomNavigationBar(navController: NavController, currentRoute: String?) {
    val items = listOf(
        BottomNavItem(Screen.Characters, Icons.Filled.Home),
        BottomNavItem(Screen.Locations, Icons.Filled.Place),
        BottomNavItem(Screen.Episodes, Icons.Filled.DateRange),
        BottomNavItem(Screen.Favorites, Icons.Filled.Favorite)
    )

    NavigationBar(
        containerColor = DarkGray
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.screen.title) },
                label = { item.screen.title?.let { Text(it) } },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    indicatorColor = Color.Black
                )
            )
        }
    }
}
