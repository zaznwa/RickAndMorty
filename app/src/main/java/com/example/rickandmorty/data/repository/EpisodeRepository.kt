package com.example.rickandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickandmorty.data.api.ApiService
import com.example.rickandmorty.data.dto.ResponseCharacterModel
import com.example.rickandmorty.data.dto.ResponseEpisodeModel
import com.example.rickandmorty.data.paging.characterpaging.CharactersPagingSource
import com.example.rickandmorty.data.paging.characterpaging.SearchCharactersPagingSource
import com.example.rickandmorty.data.paging.episodepaging.EpisodesPagingSource
import com.example.rickandmorty.data.paging.episodepaging.SearchEpisodesPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class EpisodeRepository(
    private val apiService: ApiService
) {
    fun fetchEpisodes() : Pager<Int, ResponseEpisodeModel> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                maxSize = 100,
                initialLoadSize = 60,
                prefetchDistance = 10
            ),
            pagingSourceFactory = { EpisodesPagingSource(apiService) }
        )
    }

    fun fetchEpisodeDetail(id: Int) = flow<ResponseEpisodeModel?> {
        apiService.fetchEpisode(id).body()?.let { emit(it) }
    }.flowOn(Dispatchers.IO)

    fun getSearchEpisodesPagingFlow(query: String): Flow<PagingData<ResponseEpisodeModel>> {
        return Pager(PagingConfig(pageSize = 20)) {
            SearchEpisodesPagingSource(apiService, query)
        }.flow
    }
}