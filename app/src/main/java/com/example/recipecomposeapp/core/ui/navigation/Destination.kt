package com.example.recipecomposeapp.core.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {

    @Serializable
    object Categories

    @Serializable
    object Favorites

    @Serializable
    data class Recipes(
        val categoryId: Int,
        val categoryTitle: String,
        val categoryImageUrl: String,
    )

    @Serializable
    data class RecipeDetails(
        val recipeId: Int
    )
}