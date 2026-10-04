package com.example.core.data.di

import androidx.room.Room
import com.example.core.data.local.AppDatabase
import com.example.core.data.network.ApiService
import com.example.core.data.repository.FavoriteRepositoryImpl
import com.example.core.data.repository.PokemonRepositoryImpl
import com.example.core.data.util.CoroutinesDispatcherProvider
import com.example.core.domain.repository.FavoriteRepository
import com.example.core.domain.repository.PokemonRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://pokeapi.co/api/v2/"
private const val DATABASE_NAME = "pokemon-db"

private val networkModule = module {
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
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single<ApiService> { get<Retrofit>().create(ApiService::class.java) }
}

private val databaseModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, DATABASE_NAME).build()
    }
    single { get<AppDatabase>().pokemonFavoriteDao() }
}

private val repositoryModule = module {
    single { CoroutinesDispatcherProvider() }
    single<PokemonRepository> { PokemonRepositoryImpl(get(), get()) }
    single<FavoriteRepository> { FavoriteRepositoryImpl(get()) }
}

val dataModules = listOf(networkModule, databaseModule, repositoryModule)
