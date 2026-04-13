package com.example.recipecomposeapp.ui.navigation

import kotlinx.serialization.Serializable

const val KEY_RECIPE_OBJECT = "recipe_object_key"

sealed interface Destination {

    @Serializable
    object Categories

    @Serializable
    object Favorites

    @Serializable
    data class Recipes(
        val categoryId: Int,
        val categoryTitle: String
    )

    @Serializable
    data class RecipeDetails(
        val recipeId: Int
    )
}