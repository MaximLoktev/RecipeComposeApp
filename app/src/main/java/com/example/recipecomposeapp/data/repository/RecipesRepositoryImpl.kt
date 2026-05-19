package com.example.recipecomposeapp.data.repository

import android.util.Log
import com.example.recipecomposeapp.core.network.api.RecipesApiService
import com.example.recipecomposeapp.data.database.RecipesDatabase
import com.example.recipecomposeapp.data.database.dao.toDto
import com.example.recipecomposeapp.data.database.dao.toEntity
import com.example.recipecomposeapp.data.model.CategoryDto
import com.example.recipecomposeapp.data.model.RecipeDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipesRepositoryImpl(
    private val apiService: RecipesApiService,
    database: RecipesDatabase,
) : RecipesRepository {

    private val categoryDao = database.categoryDao()

    private val recipeDao = database.recipeDao()

    override fun getCategories(): Flow<List<CategoryDto>> {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val freshData = apiService.getCategories()
                categoryDao.insertCategories(freshData.map { it.toEntity() })
            } catch (e: Exception) {
                Log.e("RecipesRepository", "Ошибка обновления категорий: ${e.message}")
            }
        }
        return categoryDao.getAllCategories().map { entities ->
            entities.map { it.toDto() }
        }
    }

    override fun getRecipesByCategory(categoryId: Int): Flow<List<RecipeDto>> {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val freshData = apiService.getRecipesByCategory(categoryId)
                recipeDao.insertRecipes(freshData.map { it.toEntity(categoryId) })
            } catch (e: Exception) {
                Log.e("RecipesRepository", "Ошибка обновления рецептов: ${e.message}")
            }
        }
        return recipeDao.getRecipesByCategoryId(categoryId).map { entities ->
            entities.map { it.toDto() }
        }
    }

    override suspend fun getRecipe(recipeId: Int): RecipeDto {
        return withContext(Dispatchers.IO) {
            try {
                apiService.getRecipe(recipeId)
            } catch (e: Exception) {
                Log.e("RecipesRepository", "Ошибка при загрузке рецепта $recipeId", e)
                throw e
            }
        }
    }
}