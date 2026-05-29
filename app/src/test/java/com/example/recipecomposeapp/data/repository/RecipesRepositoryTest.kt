package com.example.recipecomposeapp.data.repository

import app.cash.turbine.test
import com.example.recipecomposeapp.core.network.api.RecipesApiService
import com.example.recipecomposeapp.data.database.RecipesDatabase
import com.example.recipecomposeapp.data.database.dao.CategoryDao
import com.example.recipecomposeapp.data.database.dao.RecipeDao
import com.example.recipecomposeapp.data.database.entity.CategoryEntity
import com.example.recipecomposeapp.data.database.entity.RecipeEntity
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RecipesRepositoryTest {

    private val apiService = mockk<RecipesApiService>()
    private val database = mockk<RecipesDatabase>(relaxed = true)
    private val categoryDao = mockk<CategoryDao>()
    private val recipeDao = mockk<RecipeDao>()

    private lateinit var repository: RecipesRepositoryImpl

    @Before
    fun setup() {
        every { database.categoryDao() } returns categoryDao
        every { database.recipeDao() } returns recipeDao

        repository = RecipesRepositoryImpl(apiService, database)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getCategories emits categories from database`() = runTest {
        every { categoryDao.getAllCategories() } returns flowOf(
            listOf(CategoryEntity(
                id = 1,
                name = "Завтраки",
                description = "",
                imageUrl = ""
            ))
        )
        coEvery { apiService.getCategories() } returns emptyList()
        coEvery { categoryDao.insertCategories(any()) } just Runs

        repository.getCategories().test {
            val categories = awaitItem()

            assertEquals(1, categories.size)
            assertEquals("Завтраки", categories[0].title)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getCategories still emits data when api throws exception`() = runTest {
        every { categoryDao.getAllCategories() } returns flowOf(
            listOf(CategoryEntity(
                id = 1,
                name = "Обеды",
                description = "",
                imageUrl = ""
            ))
        )
        coEvery { apiService.getCategories() } throws RuntimeException("Network Error")

        repository.getCategories().test {
            val categories = awaitItem()

            assertEquals(1, categories.size)
            assertEquals("Обеды", categories[0].title)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getRecipesByCategory returns flow filtered by categoryId`() = runTest {
        every { recipeDao.getRecipesByCategoryId(1) } returns flowOf(
            listOf(RecipeEntity(
                id = 1,
                categoryId = 1,
                title = "Суп",
                ingredients = emptyList(),
                method = emptyList(),
                imageUrl = ""
            ))
        )
        coEvery { apiService.getRecipesByCategory(1) } returns emptyList()
        coEvery { recipeDao.insertRecipes(any()) } just Runs

        repository.getRecipesByCategory(1).test {
            val recipes = awaitItem()

            assertEquals(1, recipes.size)
            assertEquals("Суп", recipes[0].title)
            cancelAndIgnoreRemainingEvents()
        }
    }
}