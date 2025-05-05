package com.example.rickandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickandmorty.data.api.ApiService
import com.example.rickandmorty.data.dto.ResponseCharacterModel
import com.example.rickandmorty.data.paging.characterpaging.CharactersPagingSource
import com.example.rickandmorty.data.paging.characterpaging.SearchCharactersPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CharacterRepository(
    private val apiService: ApiService
) {
    fun fetchCharacters(): Pager<Int, ResponseCharacterModel> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                maxSize = 100,
                initialLoadSize = 60,
                prefetchDistance = 10
            ),
            pagingSourceFactory = { CharactersPagingSource(apiService) }
        )
    }

    fun fetchCharacterDetail(id: Int) = flow<ResponseCharacterModel?> {
        apiService.fetchCharacter(id).body()?.let { emit(it) }
    }.flowOn(Dispatchers.IO)

    fun getSearchCharactersPagingFlow(query: String): Flow<PagingData<ResponseCharacterModel>> {
        return Pager(PagingConfig(pageSize = 20)) {
            SearchCharactersPagingSource(apiService, query)
        }.flow
    }
}