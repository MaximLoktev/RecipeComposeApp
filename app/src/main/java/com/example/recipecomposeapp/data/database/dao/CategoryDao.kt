package com.example.recipecomposeapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipecomposeapp.data.database.entity.CategoryEntity
import com.example.recipecomposeapp.data.model.CategoryDto
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories ORDER BY name ASC")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<CategoryEntity>)
}

fun CategoryDto.toEntity() = CategoryEntity(
    id = id,
    name = title,
    description = description,
    imageUrl = imageUrl
)

fun CategoryEntity.toDto() = CategoryDto(
    id = id,
    title = name,
    description = description,
    imageUrl = imageUrl
)