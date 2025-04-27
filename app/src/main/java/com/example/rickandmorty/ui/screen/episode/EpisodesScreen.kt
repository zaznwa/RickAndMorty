package com.example.rickandmorty.ui.screen.episode

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
fun EpisodesScreen(navController: NavController, viewModel: EpisodeViewModel = koinViewModel()) {

    val episodes by viewModel.episodesFlow.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(episodes) { episode ->
            ListItem(
                id = episode.id ?: 0,
                name = episode.name ?: "Unknown",
                onClick = {
                    navController.navigate(Screen.EpisodeDetail.route + "/${episode.id}")
                }
            )
        }
    }
}
