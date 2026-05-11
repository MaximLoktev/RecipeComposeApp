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
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : ComponentActivity() {

    private var currentIntent by mutableStateOf<Intent?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("NetworkTest", "Метод onCreate() выполняется на потоке: ${Thread.currentThread().name}")

        thread {
            Log.d("NetworkTest", "Выполняю запрос на потоке: ${Thread.currentThread().name}")

            var connection: HttpURLConnection? = null

            try {
                val url = URL("https://recipes.androidsprint.ru/api/category")

                connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()

                val responseText = connection.inputStream.bufferedReader().use { it.readText() }

                Log.d("NetworkTest", "Сырой ответ от сервера:\n$responseText")

                val categories: List<CategoryDto> = Json.decodeFromString(responseText)

                Log.d("NetworkTest", "Количество полученных категорий: ${categories.size}")

                categories.forEach { category ->
                    Log.d("NetworkTest", "Категория: ${category.title}")
                }
            } catch (e: Exception) {
                Log.e("NetworkTest", "Произошла ошибка при выполнении запроса: ${e.message}", e)
            } finally {
                connection?.disconnect()
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