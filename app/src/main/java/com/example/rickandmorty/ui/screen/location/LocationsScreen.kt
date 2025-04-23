package com.example.rickandmorty.ui.screen.location

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.navigation.NavController
import com.example.rickandmorty.ui.components.CharactersItem
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.foundation.lazy.items
import com.example.rickandmorty.ui.navigation.Screen

@Composable
fun LocationsScreen(navController: NavController, viewModel: LocationViewModel = koinViewModel()) {

    val locations by viewModel.locations.observeAsState(emptyList())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(locations) { location ->
            CharactersItem(
                id = location.id ?: 0,
                name = location.name ?: "Unknown",
                onClick = {
                    navController.navigate("${Screen.LocationDetail.route}/${location.id}")
                }
            )
        }
    }
}
