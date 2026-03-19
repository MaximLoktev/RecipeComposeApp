package com.example.recipecomposeapp.data.model

data class CategoryDto(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
)

data class RecipeDto(
    val id: Int,
    val title: String,
    val ingredients: List<IngredientDto>,
    val method: List<String>,
    val imageUrl: String,
)

data class IngredientDto(
    val quantity: String,
    val unitOfMeasure: String,
    val description: String,
)