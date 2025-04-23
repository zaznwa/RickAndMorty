package com.example.rickandmorty.ui.screen.character

import android.R.attr.fontFamily
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.rickandmorty.data.dto.ResponseCharacterModel
import org.koin.compose.viewmodel.koinViewModel
import kotlin.text.toIntOrNull
import kotlin.toString

@Composable
fun CharacterDetailScreen(characterId: String, navController: NavController, viewModel: CharacterViewModel = koinViewModel()) {

    var character by remember { mutableStateOf<ResponseCharacterModel?>(null) }
    val idInt = characterId.toIntOrNull() ?: 0

    LaunchedEffect(characterId) {
        viewModel.fetchCharacterDetail(idInt) {
            character = it
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGray),
    ) {
        AsyncImage(
            model = character?.image,
            contentDescription = character?.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(380.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(6.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        ) {
            Text(text = character?.name.toString(), fontSize = 50.sp, style = TextStyle(lineHeight = 54.sp), color = White)
            Text(text = "Status: ${character?.status}", fontSize = 18.sp)
            Text(text = "Species: ${character?.species}", fontSize = 18.sp)
            Text(text = "Gender: ${character?.gender}", fontSize = 18.sp)
        }
    }
}

@Preview
@Composable
fun CharacterDetailScreenPreview() {
    CharacterDetailScreen(
        characterId = "1",
        navController = rememberNavController()
    )
}