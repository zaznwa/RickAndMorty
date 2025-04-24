package com.example.rickandmorty.data.repository

import com.example.rickandmorty.data.api.ApiService
import com.example.rickandmorty.data.dto.ResponseEpisodeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class EpisodeRepository(
    private val apiService: ApiService
) {
    fun fetchEpisodes() = flow {
        apiService.fetchAllEpisodes().body()?.results?.let { emit(it) }
    }.flowOn(Dispatchers.IO)

    fun fetchEpisodeDetail(id: Int) = flow<ResponseEpisodeModel?> {
        apiService.fetchEpisode(id).body()?.let { emit(it) }
    }.flowOn(Dispatchers.IO)
}