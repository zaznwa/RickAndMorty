package com.example.rickandmorty.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [FavoriteCharacterEntity::class], version = 3)
abstract class AppDatabase: RoomDatabase() {
    abstract fun favoriteCharactersDao(): FavoriteCharactersDao

}