package com.example.rickandmorty.data.api

import com.example.rickandmorty.data.dto.ResponseCharacterModel
import com.example.rickandmorty.data.dto.ResponseCharacters
import com.example.rickandmorty.data.dto.ResponseEpisodeModel
import com.example.rickandmorty.data.dto.ResponseEpisodes
import com.example.rickandmorty.data.dto.ResponseLocationModel
import com.example.rickandmorty.data.dto.ResponseLocations
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("api/character")
    suspend fun fetchAllCharacters(
        @Query("page") page: Int = 1
    ): Response<ResponseCharacters>

    @GET("api/character/{id}")
    suspend fun fetchCharacter(
        @Path("id") id: Int
    ): Response<ResponseCharacterModel>

    @GET("api/character")
    suspend fun searchCharacters(
        @Query("name") name: String,
        @Query("page") page: Int = 1
    ): Response<ResponseCharacters>

    @GET("api/episode")
    suspend fun fetchAllEpisodes(
        @Query("page") page: Int = 1
    ): Response<ResponseEpisodes>

    @GET("api/episode/{id}")
    suspend fun fetchEpisode(
        @Path("id") id: Int): Response<ResponseEpisodeModel>

    @GET("api/episode")
    suspend fun searchEpisode(
        @Query("name") name: String,
        @Query("page") page: Int = 1
    ): Response<ResponseEpisodes>

    @GET("api/location")
    suspend fun fetchAllLocations(
        @Query("page") page: Int = 1
    ): Response<ResponseLocations>

    @GET("api/location/{id}")
    suspend fun fetchLocation(
        @Path("id") id: Int): Response<ResponseLocationModel>

    @GET("api/location")
    suspend fun searchLocations(
        @Query("name") name: String,
        @Query("page") page: Int = 1
    ): Response<ResponseLocations>


}