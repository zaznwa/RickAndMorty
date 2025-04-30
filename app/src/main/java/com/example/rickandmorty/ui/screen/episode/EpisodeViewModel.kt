package com.example.rickandmorty.ui.screen.episode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.dto.ResponseEpisodeModel
import com.example.rickandmorty.data.repository.EpisodeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class EpisodeViewModel(
    private val repository: EpisodeRepository
) : ViewModel() {

    private val _episodesFlow = MutableStateFlow<List<ResponseEpisodeModel>>(emptyList())
    val episodesFlow: StateFlow<List<ResponseEpisodeModel>> = _episodesFlow.asStateFlow()

    private val _episodeDetailFlow = MutableStateFlow<ResponseEpisodeModel?>(null)
    val episodeDetailFlow: StateFlow<ResponseEpisodeModel?> = _episodeDetailFlow.asStateFlow()

    init {
        fetchEpisodes()
    }

    fun fetchEpisodes() = viewModelScope.launch {
        repository.fetchEpisodes()
            .catch { _episodesFlow.value = emptyList() }
            .collect { _episodesFlow.value = it }
    }

    fun searchEpisodes(query: String) = viewModelScope.launch {
        if (query.isBlank()) {
            fetchEpisodes()
        } else {
            repository.searchEpisode(query)
                .catch { _episodesFlow.value = emptyList() }
                .collect { _episodesFlow.value = it }
        }
    }

    fun fetchEpisodeDetail(id: Int) = viewModelScope.launch {
        repository.fetchEpisodeDetail(id)
            .catch { _episodeDetailFlow.value = null }
            .collect { _episodeDetailFlow.value = it }
    }
}