package com.example.rickandmorty.data.dto

data class ResponseCharacters(
    val results: List<ResponseCharacterModel>? = null
)
data class ResponseCharacterModel(
    val id: Int? = null,
    val name: String? = null,
    val status: String? = null,
    val species: String? = null,
    val type: String? = null,
    val gender: String? = null,
    val image: String? = null,
    val origin: Origin? = null,
    val location: Origin? = null,
    val episode: List<String>? = null,
    val url: String? = null,
    val created: String? = null
)

data class Origin(
    val name: String? = null,
    val url: String? = null
)