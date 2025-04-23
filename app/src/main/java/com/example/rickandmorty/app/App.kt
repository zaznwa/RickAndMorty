package com.example.rickandmorty.app

import android.app.Application
import android.media.tv.interactive.AppLinkInfo
import com.example.rickandmorty.di.dataModule
import com.example.rickandmorty.di.uiModule
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(dataModule, uiModule))
        }
    }
}