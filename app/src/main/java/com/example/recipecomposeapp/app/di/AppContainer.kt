package com.example.recipecomposeapp.app.di

import android.content.Context
import com.example.recipecomposeapp.BuildConfig
import com.example.recipecomposeapp.core.network.NetworkConfig
import com.example.recipecomposeapp.core.network.api.RecipesApiService
import com.example.recipecomposeapp.core.utils.FavoriteDataStoreManager
import com.example.recipecomposeapp.data.database.RecipesDatabase
import com.example.recipecomposeapp.data.repository.RecipesRepository
import com.example.recipecomposeapp.data.repository.RecipesRepositoryImpl
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

class AppContainer(private val context: Context) {

    private val jsonConfig: Json by lazy {
        Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
    }

    private val loggingInterceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(NetworkConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(jsonConfig.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    private val recipesApi: RecipesApiService by lazy {
        retrofit.create(RecipesApiService::class.java)
    }

    private val recipesDatabase: RecipesDatabase by lazy {
        RecipesDatabase.buildDatabase(context)
    }

    val recipesRepository: RecipesRepository by lazy {
        RecipesRepositoryImpl(
            apiService = recipesApi,
            database = recipesDatabase
        )
    }

    val favoriteManager: FavoriteDataStoreManager by lazy {
        FavoriteDataStoreManager(context)
    }
}