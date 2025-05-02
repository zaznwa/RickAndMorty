package com.example.rickandmorty.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.dao.FavoriteCharacterEntity
import com.example.rickandmorty.data.dto.ResponseCharacterModel
import com.example.rickandmorty.data.dto.ResponseCharacters
import com.example.rickandmorty.data.repository.FavoritesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: FavoritesRepository
) : ViewModel() {


    internal val searchQuery = MutableStateFlow("")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val favoriteCharactersFlow = searchQuery
        .debounce(300)
        .flatMapLatest { query ->
            if (query.isBlank()) {
                repository.fetchFavoriteCharacters()
            } else {
                repository.searchFavoriteCharacters(query)
            }
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
    }

    fun addFavoriteCharacter(character: ResponseCharacterModel) {
        val favoriteCharacterEntity = FavoriteCharacterEntity(
            id = character.id ?: 0,
            name = character.name.orEmpty(),
            status = character.status.orEmpty(),
            species = character.species.orEmpty(),
            type = character.type.orEmpty(),
            gender = character.gender.orEmpty(),
            image = character.image.orEmpty()
        )
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFavoriteCharacter(favoriteCharacterEntity)
        }
    }

    fun deleteFavoriteCharacter(character: FavoriteCharacterEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavoriteCharacter(character)
        }
    }
}