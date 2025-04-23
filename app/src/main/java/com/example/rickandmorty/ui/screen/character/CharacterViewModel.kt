package com.example.rickandmorty.ui.screen.character

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.dto.ResponseCharacterModel
import com.example.rickandmorty.data.repository.CharacterRepository
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _characters = MutableLiveData<List<ResponseCharacterModel>>()
    val characters: LiveData<List<ResponseCharacterModel>> = _characters

    init {
        fetchCharacters()
    }

    fun fetchCharacters() {
        viewModelScope.launch {
            _characters.postValue(repository.fetchCharacters())
        }
    }

    fun fetchCharacterDetail(id: Int, onResult: (ResponseCharacterModel?) -> Unit) {
        viewModelScope.launch {
            val result = repository.fetchCharacterDetail(id)
            onResult(result)
        }
    }
}