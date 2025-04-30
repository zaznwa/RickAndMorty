package com.example.rickandmorty.ui.screen.location

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.rickandmorty.ui.components.ListItem
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.example.rickandmorty.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationScreen(
    navController: NavController,
    viewModel: LocationViewModel = koinViewModel()
) {

    var searchQuery by remember { mutableStateOf("") }
    val locations by viewModel.locationsFlow.collectAsState()

    val filteredLocations = remember(locations, searchQuery) {
        if (searchQuery.isBlank()) {
            locations
        } else {
            locations.filter { location ->
                location.name?.contains(searchQuery, ignoreCase = true) == true
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            onSearch = { viewModel.searchLocations(searchQuery) },
            active = false,
            onActiveChange = {},
            placeholder = { Text("Поиск локации") },
            leadingIcon = { Icon(Icons.Default.Search, "Поиск") },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(Icons.Default.Close, "Очистить")
                    }
                }
            }
        ) {}

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(filteredLocations) { locations ->
                ListItem(
                    id = locations.id ?: 0,
                    name = locations.name ?: "Unknown",
                    onClick = {
                        navController.navigate(Screen.LocationDetail.route + "/${locations.id}")
                    }
                )
            }
        }
    }
}
