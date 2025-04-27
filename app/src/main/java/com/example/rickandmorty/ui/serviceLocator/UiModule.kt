package com.example.rickandmorty.ui.serviceLocator

import com.example.rickandmorty.ui.screen.character.CharacterViewModel
import com.example.rickandmorty.ui.screen.episode.EpisodeViewModel
import com.example.rickandmorty.ui.screen.favorite.FavoriteViewModel
import com.example.rickandmorty.ui.screen.location.LocationViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiModule: Module = module {
    viewModel { CharacterViewModel(get()) }
    viewModel { LocationViewModel(get()) }
    viewModel { EpisodeViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }

}