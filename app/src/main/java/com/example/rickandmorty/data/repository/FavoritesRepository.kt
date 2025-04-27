package com.example.rickandmorty.data.repository

import com.example.rickandmorty.data.dao.FavoriteCharacterEntity
import com.example.rickandmorty.data.dao.FavoriteCharactersDao
import kotlinx.coroutines.flow.Flow

class FavoritesRepository(private val dao: FavoriteCharactersDao) {

    fun fetchFavoriteCharacters(): Flow<List<FavoriteCharacterEntity>> = dao.fetchAllFavoriteCharacters()

    suspend fun insertFavoriteCharacter(character: FavoriteCharacterEntity) {
        dao.insertFavoriteCharacter(character)
    }

    suspend fun deleteFavoriteCharacter(character: FavoriteCharacterEntity) {
        dao.deleteFavoriteCharacter(character)
    }
}
