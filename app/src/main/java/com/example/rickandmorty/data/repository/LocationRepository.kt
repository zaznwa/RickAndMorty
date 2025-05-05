package com.example.rickandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickandmorty.data.api.ApiService
import com.example.rickandmorty.data.dto.ResponseEpisodeModel
import com.example.rickandmorty.data.dto.ResponseLocationModel
import com.example.rickandmorty.data.paging.episodepaging.EpisodesPagingSource
import com.example.rickandmorty.data.paging.episodepaging.SearchEpisodesPagingSource
import com.example.rickandmorty.data.paging.locationpaging.LocationsPagingSource
import com.example.rickandmorty.data.paging.locationpaging.SearchLocationsPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class LocationRepository(
    private val apiService: ApiService
) {
    fun fetchLocations()  : Pager<Int, ResponseLocationModel> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                maxSize = 100,
                initialLoadSize = 60,
                prefetchDistance = 10
            ),
            pagingSourceFactory = { LocationsPagingSource(apiService) }
        )
    }

    fun fetchLocationDetail(id: Int) = flow<ResponseLocationModel?> {
        apiService.fetchLocation(id).body()?.let { emit(it) }
    }.flowOn(Dispatchers.IO)

    fun getSearchLocationsPagingFlow(query: String): Flow<PagingData<ResponseLocationModel>> {
        return Pager(PagingConfig(pageSize = 20)) {
            SearchLocationsPagingSource(apiService, query)
        }.flow
    }
}