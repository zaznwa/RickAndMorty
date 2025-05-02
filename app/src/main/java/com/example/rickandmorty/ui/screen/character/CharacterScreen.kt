package com.example.rickandmorty.ui.screen.character

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.rememberAsyncImagePainter
import com.example.rickandmorty.data.dto.ResponseCharacterModel
import com.example.rickandmorty.ui.navigation.Screen
import org.koin.compose.viewmodel.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersScreen(
    navController: NavController,
    viewModel: CharacterViewModel = koinViewModel()
) {

    var searchQuery by remember { mutableStateOf("") }
    val charactersFlow by viewModel.charactersFlow.collectAsState()

    val filteredCharacters = remember(charactersFlow, searchQuery) {
        if (searchQuery.isBlank()) {
            charactersFlow
        } else {
            charactersFlow.filter { character ->
                character.name?.contains(searchQuery, ignoreCase = true) == true
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGray)
    ) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(  horizontal = 8.dp),
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            onSearch = { viewModel.searchCharacters(searchQuery) },
            active = false,
            onActiveChange = {},
            placeholder = { Text("Поиск персонажей") },
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
            items(filteredCharacters) { character ->
                CharactersItem(character = character, navController = navController)
            }
        }
    }
}

@Composable
fun CharactersItem(
    character: ResponseCharacterModel,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Gray
        ),
        onClick = {
            navController.navigate("${Screen.CharacterDetail.route}/${character.id}")
        }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                modifier = Modifier
                    .width(100.dp)
                    .fillMaxHeight(),
                contentDescription = "Character image",
                contentScale = ContentScale.Crop,
                painter = rememberAsyncImagePainter(character.image),
            )
            Column(
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center

            ) {
                Text(
                    text = character.name.toString(), color = White, fontSize = 26.sp
                )
                Column(
                    modifier = Modifier.padding(top = 4.dp),
                ) {

                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = "${character.status}",
                        color = White,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharactersScreenPreview() {
    val navController = rememberNavController()
    CharactersScreen(navController = navController)
}
