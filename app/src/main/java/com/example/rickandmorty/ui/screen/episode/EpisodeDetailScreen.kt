package com.example.rickandmorty.ui.screen.episode

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.rickandmorty.data.dto.ResponseEpisodeModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EpisodeDetailScreen(
    episodeId: String,
    navController: NavController,
    viewModel: EpisodeViewModel = koinViewModel()
) {
    val episode by viewModel.episodeDetailFlow.collectAsState()
    val idInt = episodeId.toIntOrNull() ?: 0

    LaunchedEffect(episodeId) {
        viewModel.fetchEpisodeDetail(idInt)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGray)
            .padding(16.dp)
    ) {
        val name = episode?.name.toString()
        val airDate = episode?.air_date ?: "Unknown"
        val episodeCode = episode?.episode ?: "Unknown"
        val characters = episode?.characters?.joinToString(separator = ", ") ?: "Unknown"
        val created = episode?.created ?: "Unknown"

        Text(
            text = name,
            fontSize = 50.sp,
            style = TextStyle(lineHeight = 54.sp),
            color = White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Air Date: $airDate", fontSize = 18.sp)
        Text(text = "Episode Code: $episodeCode", fontSize = 18.sp)
        Text(text = "Created: $created", fontSize = 18.sp)
    }
}

@Preview
@Composable
fun PreviewEpisodeDetailScreen() {
    EpisodeDetailScreen(
        episodeId = "1",
        navController = rememberNavController()
    )
}