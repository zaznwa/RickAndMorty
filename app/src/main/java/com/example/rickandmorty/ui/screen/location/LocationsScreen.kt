package com.example.rickandmorty.ui.screen.location

import android.graphics.pdf.models.ListItem
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.navigation.NavController
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LocationsScreen(navController: NavController, viewModel: LocationViewModel = koinViewModel()) {

    val locations by viewModel.locations.observeAsState(emptyList())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGray)
    ) {
        items(locations) { location ->
            ListItem(
                id = location.id ?: 0,
                name = location.name ?: "Unknown",
                onClick = {
                    navController.navigate("${Screen.LocationDetail.route}/${location.id}")
                }
            )
        }
    }
}
