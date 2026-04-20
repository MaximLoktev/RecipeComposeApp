package com.example.recipecomposeapp.ui.utils

import android.content.Context
import androidx.core.content.edit

class FavoritePrefsManager(context: Context) {

    private val FILE_NAME_KEY = "recipe_app_prefs"

    private val FAVORITES_KEY = "favorite_recipe_ids"

    private val prefs = context.getSharedPreferences(FILE_NAME_KEY, Context.MODE_PRIVATE)

    fun getAllFavorites(): Set<String> {
        return prefs.getStringSet(FAVORITES_KEY, emptySet()) ?: emptySet()
    }

    fun isFavorite(recipeId: Int): Boolean {
        return getAllFavorites().contains(recipeId.toString())
    }

    fun addToFavorites(recipeId: Int) {
        val updatedFavorites = getAllFavorites().toMutableSet()
        updatedFavorites.add(recipeId.toString())

        prefs.edit { putStringSet(FAVORITES_KEY, updatedFavorites) }
    }

    fun removeFromFavorites(recipeId: Int) {
        val updatedFavorites = getAllFavorites().toMutableSet()
        updatedFavorites.remove(recipeId.toString())

        prefs.edit { putStringSet(FAVORITES_KEY, updatedFavorites) }
    }
}