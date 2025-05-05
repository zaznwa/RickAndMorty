package com.example.rickandmorty.ui.screen.episode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmorty.data.dto.ResponseCharacterModel
import com.example.rickandmorty.data.dto.ResponseEpisodeModel
import com.example.rickandmorty.data.repository.EpisodeRepository
import kotlinx.coroutines.Dispatchers
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

class EpisodeViewModel(
    private val repository: EpisodeRepository
) : ViewModel() {

    private val _episodesFlow = MutableStateFlow<PagingData<ResponseEpisodeModel>>(PagingData.empty())
    val episodesFlow = _episodesFlow.asStateFlow()

    private val _episodeDetailFlow = MutableStateFlow<ResponseEpisodeModel?>(null)
    val episodeDetailFlow: StateFlow<ResponseEpisodeModel?> = _episodeDetailFlow.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val searchEpisodesFlow = _searchQuery
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            if (query.isBlank()) {
                flowOf(PagingData.empty())
            } else {
                repository.getSearchEpisodesPagingFlow(query)
            }
        }
        .cachedIn(viewModelScope)


    init {
        fetchEpisodes()
    }

    fun fetchEpisodes() = viewModelScope.launch(Dispatchers.IO) {
        repository.fetchEpisodes().flow.cachedIn(viewModelScope)
            .collectLatest { _episodesFlow.value = it }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun fetchEpisodeDetail(id: Int) = viewModelScope.launch {
        repository.fetchEpisodeDetail(id)
            .catch { _episodeDetailFlow.value = null }
            .collect { _episodeDetailFlow.value = it }
    }
}