package com.example.rickandmorty.ui.screen.location

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.rickandmorty.ui.components.ListItem
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.rickandmorty.ui.navigation.Screen

@Composable
fun LocationsScreen(
    navController: NavController,
    viewModel: LocationViewModel = koinViewModel()
) {

    val locations by viewModel.locationsFlow.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(locations) { location ->
            ListItem(
                id = location.id ?: 0, name = location.name ?: "Unknown", onClick = {
                    navController.navigate("${Screen.LocationDetail.route}/${location.id}")
                })
        }
    }
}
