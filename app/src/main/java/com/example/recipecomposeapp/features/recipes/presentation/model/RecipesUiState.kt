package com.example.recipecomposeapp.features.recipes.presentation.model

data class RecipesUiState(
    val categoryTitle: String = "",
    val categoryImageUrl: String = "",
    val recipes: List<RecipeUiModel> = emptyList(),
    val isLoading: Boolean = false
) {
    val isEmpty: Boolean
        get() = !isLoading && recipes.isEmpty()
}