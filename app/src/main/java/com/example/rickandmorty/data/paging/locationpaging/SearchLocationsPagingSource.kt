package com.example.rickandmorty.data.paging.locationpaging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.data.api.ApiService
import com.example.rickandmorty.data.dto.ResponseLocationModel

class SearchLocationsPagingSource(
    private val apiService: ApiService,
    private val query: String
) : PagingSource<Int, ResponseLocationModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResponseLocationModel> {
        return try {
            val page = params.key ?: 1
            val response = apiService.searchLocations(query, page)

            val locations = response.body()?.results ?: emptyList()

            LoadResult.Page(
                data = locations,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (locations.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResponseLocationModel>): Int? = null
}
