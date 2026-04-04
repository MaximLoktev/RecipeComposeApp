package com.example.recipecomposeapp.ui.navigation

import android.net.Uri

const val KEY_RECIPE_OBJECT = "recipe_object_key"

sealed class Destination(val route: String) {
    object Categories : Destination("categories")

    object Favorites : Destination("favorites")

    object Recipes : Destination("recipes/{categoryId}/{categoryTitle}") {

        fun createRoute(categoryId: Int, categoryTitle: String): String {
            val encodedTitle = Uri.encode(categoryTitle)
            return "recipes/$categoryId/$encodedTitle"
        }
    }

    object RecipeDetails : Destination("recipes/{recipeId}") {

        fun createRoute(recipeId: Int): String = "recipes/$recipeId"
    }
}