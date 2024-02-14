package com.devrev.app

import android.app.Application
import com.devrev.network.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieListApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MovieListApplication)
            modules(listOf(networkModule, appModule))
        }
    }
}