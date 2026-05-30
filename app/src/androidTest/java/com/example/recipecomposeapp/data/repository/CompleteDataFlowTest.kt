package com.example.recipecomposeapp.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.example.recipecomposeapp.core.network.api.RecipesApiService
import com.example.recipecomposeapp.data.database.RecipesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.converter.kotlinx.serialization.asConverterFactory

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class CompleteDataFlowTest {

    private lateinit var database: RecipesDatabase
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(context, RecipesDatabase::class.java).build()
        mockWebServer = MockWebServer().also { it.start() }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        database.close()
        mockWebServer.shutdown()
    }

    @Test
    fun categoriesAreLoadedFromApiAndStoredInCache() = runTest {
        val jsonResponse = """
            [
                {
                    "id": 1,
                    "title": "Завтраки",
                    "description": "Лёгкие блюда",
                    "imageUrl": "breakfast.jpg"
                }
            ]
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setBody(jsonResponse)
                .setResponseCode(200)
        )

        val json = Json { ignoreUnknownKeys = true }

        val apiService = retrofit2.Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(RecipesApiService::class.java)

        val repository = RecipesRepositoryImpl(apiService = apiService, database = database)

        repository.getCategories().test {
            var categories = awaitItem()

            if (categories.isEmpty()) {
                categories = awaitItem()
            }

            assertEquals(1, categories.size)
            assertEquals("Завтраки", categories.first().title)
            cancelAndIgnoreRemainingEvents()
        }

        val cached = database.categoryDao().getAllCategories().first()
        assertEquals(1, cached.size)
        assertEquals("Завтраки", cached.first().name)
    }
}