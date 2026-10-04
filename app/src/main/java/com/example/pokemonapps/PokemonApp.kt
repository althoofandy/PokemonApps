package com.example.pokemonapps

import android.app.Application
import com.example.core.data.di.dataModules
import com.example.core.domain.di.domainModule
import com.example.feature.detail.di.detailModule
import com.example.feature.favorite.di.favoriteModule
import com.example.feature.pokedex.di.pokedexModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin


class PokemonApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@PokemonApp)
            modules(dataModules + domainModule + pokedexModule + detailModule + favoriteModule)
        }
    }
}
