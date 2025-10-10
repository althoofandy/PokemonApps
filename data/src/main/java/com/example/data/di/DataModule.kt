package com.example.data.di

import androidx.room.Room
import com.example.core.model.CoroutinesDispatcherProvider
import com.example.core.network.ApiService
import com.example.data.local.AppDatabase
import com.example.data.repository.PokemonLocalRepository
import com.example.data.repository.PokemonLocalRepositoryImpl
import com.example.data.repository.PokemonRepository
import com.example.data.repository.PokemonRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val dataModule = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "pokemon-db"
        ).build()
    }
    single { CoroutinesDispatcherProvider() }
    single { get<AppDatabase>().pokemonFavoriteDao() }
    single<PokemonRepository> { PokemonRepositoryImpl(get(), get()) }
    single<PokemonLocalRepository> { PokemonLocalRepositoryImpl(get()) }
}

val networkModule = module {
    single {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single<ApiService> {
        get<Retrofit>().create(ApiService::class.java)
    }
}

val dataModules = listOf(
    networkModule,
    dataModule
)
