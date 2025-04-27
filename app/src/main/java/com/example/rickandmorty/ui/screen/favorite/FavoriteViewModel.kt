package com.example.rickandmorty.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.dao.FavoriteCharacterEntity
import com.example.rickandmorty.data.dto.ResponseCharacterModel
import com.example.rickandmorty.data.dto.ResponseCharacters
import com.example.rickandmorty.data.repository.FavoritesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: FavoritesRepository
) : ViewModel() {
    val favoriteCharactersFlow: Flow<List<FavoriteCharacterEntity>> =
        repository.fetchFavoriteCharacters()

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