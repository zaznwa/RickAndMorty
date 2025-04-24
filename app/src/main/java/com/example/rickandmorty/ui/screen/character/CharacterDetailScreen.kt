package com.example.rickandmorty.ui.screen.character

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.example.rickandmorty.data.dao.FavoriteCharacterEntity
import com.example.rickandmorty.data.dto.ResponseCharacterModel
import com.example.rickandmorty.ui.components.CharacterDetailCard
import com.example.rickandmorty.ui.screen.favorite.FavoriteViewModel
import org.koin.compose.viewmodel.koinViewModel
import kotlin.text.toIntOrNull
import kotlin.toString

@Composable
fun CharacterDetailScreen(
    characterId: String,
    navController: NavController,
    viewModel: CharacterViewModel = koinViewModel(),
    favoritesViewModel: FavoriteViewModel = koinViewModel()
) {
    val idInt = characterId.toIntOrNull() ?: 0

    LaunchedEffect(idInt) {
        viewModel.fetchCharacterDetail(idInt)
    }

    val character by viewModel.characterDetailFlow.collectAsState()
    val favoriteCharacters by favoritesViewModel.favoriteCharactersFlow.collectAsState(initial = emptyList())

    val isFavorite = favoriteCharacters.any { it.id == character?.id }




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
        CharacterDetailCard(
            character = character,
            isFavorite = isFavorite,
            onFavoriteClick = {
                character?.let {
                    if (isFavorite) {
                        favoritesViewModel.deleteFavoriteCharacter(it as FavoriteCharacterEntity)
                    } else {
                        favoritesViewModel.addFavoriteCharacter(it)
                    }
                }
            },
            modifier = Modifier.padding(16.dp)
        )
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