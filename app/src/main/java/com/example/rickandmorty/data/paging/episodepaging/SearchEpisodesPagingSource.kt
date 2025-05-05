package com.example.rickandmorty.data.paging.episodepaging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.data.api.ApiService
import com.example.rickandmorty.data.dto.ResponseEpisodeModel

class SearchEpisodesPagingSource(
    private val apiService: ApiService,
    private val query: String
) : PagingSource<Int, ResponseEpisodeModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResponseEpisodeModel> {
        return try {
            val page = params.key ?: 1
            val response = apiService.searchEpisode(query, page)

            val episodes = response.body()?.results ?: emptyList()

            LoadResult.Page(
                data = episodes,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (episodes.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResponseEpisodeModel>): Int? = null
}
