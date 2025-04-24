package com.example.rickandmorty.ui.screen.character


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.dto.ResponseCharacterModel
import com.example.rickandmorty.data.repository.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _charactersFlow = MutableStateFlow<List<ResponseCharacterModel>>(emptyList())
    val charactersFlow: StateFlow<List<ResponseCharacterModel>> = _charactersFlow.asStateFlow()

    private val _characterDetailFlow = MutableStateFlow<ResponseCharacterModel?>(null)
    val characterDetailFlow: StateFlow<ResponseCharacterModel?> = _characterDetailFlow.asStateFlow()

    init {
        fetchCharacters()
    }

    fun fetchCharacters() = viewModelScope.launch {
        repository.fetchCharacters()
            .catch { _charactersFlow.value = emptyList() }
            .collect { _charactersFlow.value = it }
    }

    fun fetchCharacterDetail(id: Int) = viewModelScope.launch {
        repository.fetchCharacterDetail(id)
            .catch { _characterDetailFlow.value = null }
            .collect { _characterDetailFlow.value = it }
    }
}