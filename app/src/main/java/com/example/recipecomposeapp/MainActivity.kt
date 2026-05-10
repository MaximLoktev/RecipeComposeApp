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
import com.example.recipecomposeapp.data.model.CategoryDto
import com.example.recipecomposeapp.data.model.RecipeDto
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : ComponentActivity() {

    private var currentIntent by mutableStateOf<Intent?>(null)

    private val okHttpClient = OkHttpClient()

    private val threadPool: ExecutorService = Executors.newFixedThreadPool(10)

    private val jsonConfig = Json { ignoreUnknownKeys = true }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("OkHttpTest", "Метод onCreate() выполняется на потоке: ${Thread.currentThread().name}")

        threadPool.execute {
            try {
                Log.d("OkHttpTest", "Выполняю запрос категорий на потоке: ${Thread.currentThread().name}")

                val categoryRequest = Request.Builder()
                    .url("https://recipes.androidsprint.ru/api/category")
                    .build()

                val responseText = okHttpClient.newCall(categoryRequest).execute().body.string()

                val categories: List<CategoryDto> = jsonConfig.decodeFromString(responseText)

                Log.d("OkHttpTest", "Количество полученных категорий: ${categories.size}")

                categories.forEach { category ->
                    threadPool.execute {
                        try {
                            Log.d("OkHttpTest", "Запрашиваю рецепты для '${category.title}' на потоке: ${Thread.currentThread().name}")

                            val recipesRequest = Request.Builder()
                                .url("https://recipes.androidsprint.ru/api/category/${category.id}/recipes")
                                .build()

                            val recipesText = okHttpClient.newCall(recipesRequest).execute().body.string()

                            val recipes: List<RecipeDto> = jsonConfig.decodeFromString(recipesText)

                            Log.d("OkHttpTest", "Категория '${category.title}' -> загружено рецептов: ${recipes.size} (Поток: ${Thread.currentThread().name})")

                        } catch (e: Exception) {
                            Log.e("OkHttpTest", "Ошибка загрузки рецептов для '${category.title}': ${e.message}", e)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("OkHttpTest", "Ошибка загрузки списка категорий: ${e.message}", e)
            }
        }

        enableEdgeToEdge()

        currentIntent = intent

        setContent {
            RecipesApp(currentIntent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        threadPool.shutdown()
        Log.d("Pool", "Пул потоков остановлен в onDestroy()")
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        setIntent(intent)

        currentIntent = intent
    }
}