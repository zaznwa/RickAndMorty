package com.example.rickandmorty.ui.screen.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmorty.data.dto.ResponseEpisodeModel
import com.example.rickandmorty.data.dto.ResponseLocationModel
import com.example.rickandmorty.data.repository.LocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class LocationViewModel(
    private val repository: LocationRepository
) : ViewModel() {

    private val _locationsFlow =
        MutableStateFlow<PagingData<ResponseLocationModel>>(PagingData.empty())
    val locationsFlow = _locationsFlow.asStateFlow()

    private val _locationDetailFlow = MutableStateFlow<ResponseLocationModel?>(null)
    val locationDetailFlow: StateFlow<ResponseLocationModel?> = _locationDetailFlow.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val searchLocationsFlow = _searchQuery
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            if (query.isBlank()) {
                flowOf(PagingData.empty())
            } else {
                repository.getSearchLocationsPagingFlow(query)
            }
        }

    init {
        fetchLocations()
    }

    fun fetchLocations() = viewModelScope.launch(Dispatchers.IO) {
        repository.fetchLocations()
            .flow
            .cachedIn(viewModelScope)
            .collect { _locationsFlow.value = it }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun fetchLocationDetail(id: Int) = viewModelScope.launch {
        repository.fetchLocationDetail(id)
            .catch { _locationDetailFlow.value = null }
            .collect { detail ->
                _locationDetailFlow.value = detail
            }
    }
}