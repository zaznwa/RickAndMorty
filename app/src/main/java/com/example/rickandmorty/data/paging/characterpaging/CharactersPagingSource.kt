package com.example.rickandmorty.data.paging.characterpaging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil3.network.HttpException
import com.example.rickandmorty.data.api.ApiService
import com.example.rickandmorty.data.dto.ResponseCharacterModel
import okio.IOException

class CharactersPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, ResponseCharacterModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResponseCharacterModel> {
        val page = params.key ?: 1
        return try {
            val response = apiService.fetchAllCharacters(page)
            if (response.isSuccessful && response.body() != null) {
                val data = response.body()?.results ?: emptyList()
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (data.isEmpty()) null else page + 1

                return LoadResult.Page(
                    data = data,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            } else {
                LoadResult.Error(Exception(response.message()))
            }

        } catch (ex: IOException) {
            LoadResult.Error(ex)
        } catch (ex: HttpException) {
            LoadResult.Error(ex)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResponseCharacterModel>): Int? {
        return state.anchorPosition?.let { anchorPos ->
            state.closestPageToPosition(anchorPos)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPos)?.nextKey?.minus(1)
        }
    }
}