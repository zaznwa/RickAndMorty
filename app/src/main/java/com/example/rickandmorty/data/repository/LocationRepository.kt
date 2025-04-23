package com.example.rickandmorty.data.repository

import com.example.rickandmorty.data.api.ApiService
import com.example.rickandmorty.data.dto.ResponseLocationModel


class LocationRepository(
    private val apiService: ApiService
) {
    suspend fun fetchLocations(): List<ResponseLocationModel>? {
        val response = apiService.fetchAllLocations()
        return if (response.isSuccessful) response.body()?.results else null
    }

    suspend fun fetchLocationDetail(id: Int): ResponseLocationModel? {
        val response = apiService.fetchLocation(id)
        return if (response.isSuccessful) response.body() else null
    }
}