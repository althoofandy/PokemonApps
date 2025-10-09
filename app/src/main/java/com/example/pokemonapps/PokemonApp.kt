package com.example.pokemonapps

import android.app.Application
import com.example.data.di.dataModules
import com.example.features.di.featureModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin


class PokemonApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@PokemonApp)
            modules(dataModules + featureModule)
        }
    }
}
