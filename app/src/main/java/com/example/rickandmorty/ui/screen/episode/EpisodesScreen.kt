package com.example.rickandmorty.ui.screen.episode

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.rickandmorty.ui.components.ListItem
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickandmorty.ui.navigation.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodesScreen(navController: NavController, viewModel: EpisodeViewModel = koinViewModel()) {

    var searchQuery by remember { mutableStateOf("") }
    val pagingEpisodes = viewModel.episodesFlow.collectAsLazyPagingItems()
    val searchEpisodes = viewModel.searchEpisodesFlow.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGray)
    ) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            query = searchQuery,
            onQueryChange = {
                searchQuery = it
                viewModel.setSearchQuery(it)
            },
            onSearch = {},
            active = false,
            onActiveChange = {},
            placeholder = { Text("Поиск эпизодов") },
            leadingIcon = { Icon(Icons.Default.Search, "Поиск") },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = {
                        searchQuery = ""
                        viewModel.setSearchQuery("")
                    }) {
                        Icon(Icons.Default.Close, "Очистить")
                    }
                }
            }
        ) { }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            val listToShow = if (searchQuery.isNotBlank()) searchEpisodes else pagingEpisodes

            items(listToShow.itemCount) { index ->
                listToShow[index]?.let { episode ->
                    ListItem(
                        id = episode.id,
                        name = episode.name.toString(),
                        onClick = {
                            navController.navigate("${Screen.EpisodeDetail.route}/${episode.id}")
                        }
                    )
                }
            }

            listToShow.apply {
                when {
                    loadState.append is LoadState.Loading -> {
                        item { CircularProgressIndicator(modifier = Modifier.padding(16.dp)) }
                    }

                    loadState.refresh is LoadState.Error -> {
                        val e = loadState.refresh as LoadState.Error
                        item { Text("Ошибка: ${e.error.message}") }
                    }
                }
            }
        }
    }
}
