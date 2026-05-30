package com.example.recipecomposeapp.data.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.recipecomposeapp.data.database.RecipesDatabase
import com.example.recipecomposeapp.data.database.entity.CategoryEntity
import com.example.recipecomposeapp.data.database.entity.RecipeEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecipeDaoTest {

    private lateinit var database: RecipesDatabase
    private lateinit var categoryDao: CategoryDao
    private lateinit var recipeDao: RecipeDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(context, RecipesDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        categoryDao = database.categoryDao()
        recipeDao = database.recipeDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertsAndRetrievesCategories() = runTest {
        val categories = listOf(
            CategoryEntity(id = 1, name = "Завтраки", description = "Лёгкие", imageUrl = ""),
            CategoryEntity(id = 2, name = "Обеды", description = "Сытные", imageUrl = "")
        )

        categoryDao.insertCategories(categories)

        val retrieved = categoryDao.getAllCategories().first()

        assertEquals(2, retrieved.size)
    }

    @Test
    fun insertReplacesDuplicateCategory() = runTest {
        val cat1 = CategoryEntity(id = 1, name = "Old", description = "", imageUrl = "")
        val cat2 = CategoryEntity(id = 1, name = "New", description = "", imageUrl = "")

        categoryDao.insertCategories(listOf(cat1))
        categoryDao.insertCategories(listOf(cat2))

        val retrieved = categoryDao.getAllCategories().first()

        assertEquals(1, retrieved.size)
        assertEquals("New", retrieved[0].name)
    }

    @Test
    fun getRecipesByCategoryReturnsCorrectItems() = runTest {
        val recipes = listOf(
            RecipeEntity(
                id = 1,
                categoryId = 1,
                title = "Суп",
                ingredients = emptyList(),
                method = emptyList(),
                imageUrl = ""
            ),
            RecipeEntity(
                id = 2,
                categoryId = 2,
                title = "Торт",
                ingredients = emptyList(),
                method = emptyList(),
                imageUrl = ""
            )
        )

        recipeDao.insertRecipes(recipes)

        val category1Recipes = recipeDao.getRecipesByCategoryId(1).first()

        assertEquals(1, category1Recipes.size)
        assertEquals("Суп", category1Recipes[0].title)
    }

    @Test
    fun emptyDatabaseReturnsEmptyList() = runTest {
        val retrieved = categoryDao.getAllCategories().first()

        assertTrue(retrieved.isEmpty())
    }
}