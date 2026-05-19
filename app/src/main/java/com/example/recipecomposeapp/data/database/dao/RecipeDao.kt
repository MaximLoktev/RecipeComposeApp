package com.example.recipecomposeapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipecomposeapp.data.database.entity.RecipeEntity
import com.example.recipecomposeapp.data.model.IngredientDto
import com.example.recipecomposeapp.data.model.RecipeDto
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): Flow<List<RecipeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<RecipeEntity>)

    @Query("SELECT * FROM recipes WHERE categoryId = :categoryId")
    fun getRecipesByCategoryId(categoryId: Int): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipes WHERE id = :recipeId")
    fun getRecipeById(recipeId: Int): Flow<RecipeEntity?>

    @Query("SELECT * FROM recipes WHERE id IN (:recipeIds)")
    fun getRecipesByIds(recipeIds: List<Int>): Flow<List<RecipeEntity>>
}

fun RecipeDto.toEntity(categoryId: Int) = RecipeEntity(
    id = id,
    title = title,
    categoryId = categoryId,
    imageUrl = imageUrl,
    ingredients = ingredients.map {
        "${it.quantity}:${it.unitOfMeasure}:${it.description}"
    },
    method = method
)

fun RecipeEntity.toDto() = RecipeDto(
    id = id,
    title = title,
    imageUrl = imageUrl,
    ingredients = ingredients.map { ingredientString ->
        val parts = ingredientString.split(":", limit = 3)

        IngredientDto(
            quantity = parts.getOrNull(0)?.trim() ?: "",
            unitOfMeasure = parts.getOrNull(1)?.trim() ?: "",
            description = parts.getOrNull(2)?.trim() ?: "",
        )
    },
    method = method
)