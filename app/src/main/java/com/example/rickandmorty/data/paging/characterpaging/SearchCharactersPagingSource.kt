package com.example.rickandmorty.data.paging.characterpaging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.data.api.ApiService
import com.example.rickandmorty.data.dto.ResponseCharacterModel

class SearchCharactersPagingSource(
    private val apiService: ApiService,
    private val query: String
) : PagingSource<Int, ResponseCharacterModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResponseCharacterModel> {
        return try {
            val page = params.key ?: 1
            val response = apiService.searchCharacters(query, page)

            val characters = response.body()?.results ?: emptyList()

            LoadResult.Page(
                data = characters,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (characters.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResponseCharacterModel>): Int? = null
}
