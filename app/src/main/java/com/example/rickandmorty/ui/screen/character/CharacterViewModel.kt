package com.example.rickandmorty.ui.screen.character


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmorty.data.dto.ResponseCharacterModel
import com.example.rickandmorty.data.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _charactersFlow = MutableStateFlow<PagingData<ResponseCharacterModel>>(PagingData.empty())
    val charactersFlow = _charactersFlow.asStateFlow()

    private val _characterDetailFlow = MutableStateFlow<ResponseCharacterModel?>(null)
    val characterDetailFlow: StateFlow<ResponseCharacterModel?> = _characterDetailFlow.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val searchCharactersFlow = _searchQuery
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            if (query.isBlank()) {
                flowOf(PagingData.empty())
            } else {
                repository.getSearchCharactersPagingFlow(query)
            }
        }
        .cachedIn(viewModelScope)

    init {
        fetchCharacters()
    }

    fun fetchCharacters() = viewModelScope.launch(Dispatchers.IO) {
        repository.fetchCharacters().flow.cachedIn(viewModelScope)
            .collectLatest { _charactersFlow.value = it }
    }
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun fetchCharacterDetail(id: Int) = viewModelScope.launch {
        repository.fetchCharacterDetail(id)
            .catch { _characterDetailFlow.value = null }
            .collect { _characterDetailFlow.value = it }
    }
}