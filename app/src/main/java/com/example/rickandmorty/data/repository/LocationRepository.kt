package com.example.rickandmorty.data.repository

import com.example.rickandmorty.data.api.ApiService
import com.example.rickandmorty.data.dto.ResponseLocationModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class LocationRepository(
    private val apiService: ApiService
) {
    fun fetchLocations() = flow {
        apiService.fetchAllLocations().body()?.results?.let { emit(it) }
    }.flowOn(Dispatchers.IO)

    fun fetchLocationDetail(id: Int) = flow<ResponseLocationModel?> {
        apiService.fetchLocation(id).body()?.let { emit(it) }
    }.flowOn(Dispatchers.IO)

    fun searchLocations(name: String) = flow {
        apiService.searchLocations(name).body()?.results?.let { emit(it) }
    }.flowOn(Dispatchers.IO)
}