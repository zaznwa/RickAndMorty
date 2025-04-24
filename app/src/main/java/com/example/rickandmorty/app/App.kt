package com.example.rickandmorty.app

import android.app.Application
import com.example.rickandmorty.data.serviceLocator.dataModule
import com.example.rickandmorty.ui.serviceLocator.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(dataModule, uiModule))
        }
    }
}