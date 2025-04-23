package com.example.rickandmorty.data.repository

import com.example.rickandmorty.data.api.ApiService
import com.example.rickandmorty.data.dto.ResponseEpisodeModel


class EpisodeRepository(
    private val apiService: ApiService
) {
    suspend fun fetchEpisodes(): List<ResponseEpisodeModel>? {
        val response = apiService.fetchAllEpisodes()
        return if (response.isSuccessful) response.body()?.results else null
    }

    suspend fun fetchEpisodeDetail(id: Int): ResponseEpisodeModel? {
        val response = apiService.fetchEpisode(id)
        return if (response.isSuccessful) response.body() else null
    }
}