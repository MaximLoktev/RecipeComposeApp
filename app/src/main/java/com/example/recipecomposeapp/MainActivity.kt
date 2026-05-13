package com.example.recipecomposeapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import com.example.recipecomposeapp.core.network.NetworkConfig
import com.example.recipecomposeapp.core.network.api.RecipesApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class MainActivity : ComponentActivity() {

    private var currentIntent by mutableStateOf<Intent?>(null)

    private val jsonConfig = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(NetworkConfig.BASE_URL)
        .addConverterFactory(jsonConfig.asConverterFactory("application/json".toMediaType()))
        .build()

    private val apiService = retrofit.create(RecipesApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            try {
                val categories = apiService.getCategories()

                Log.d("RetrofitTest", "Количество полученных категорий: ${categories.size}")

                categories.forEach { category ->
                    try {
                        val recipes = apiService.getRecipesByCategory(category.id)

                        Log.d("RetrofitTest", "Категория '${category.title}' -> загружено рецептов: ${recipes.size}")

                    } catch (e: Exception) {
                        Log.e("RetrofitTest", "Ошибка загрузки рецептов для '${category.title}': ${e.message}", e)
                    }
                }
            } catch (e: Exception) {
                Log.e("RetrofitTest", "Ошибка загрузки списка категорий: ${e.message}", e)
            }
        }

        enableEdgeToEdge()

        currentIntent = intent

        setContent {
            RecipesApp(currentIntent)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        setIntent(intent)

        currentIntent = intent
    }
}