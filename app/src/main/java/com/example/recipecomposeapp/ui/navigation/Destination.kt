package com.example.recipecomposeapp.ui.navigation

import android.net.Uri

sealed class Destination(val route: String) {
    object Categories : Destination("categories")

    object Favorites : Destination("favorites")

    object Recipes : Destination("recipes/{categoryId}/{categoryTitle}") {

        fun createRoute(categoryId: Int, categoryTitle: String): String {
            val encodedTitle = Uri.encode(categoryTitle)
            return "recipes/$categoryId/$encodedTitle"
        }
    }
}