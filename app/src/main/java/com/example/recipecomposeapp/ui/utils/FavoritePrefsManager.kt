package com.example.recipecomposeapp.ui.utils

import android.content.Context
import androidx.core.content.edit
import com.example.recipecomposeapp.Constants

class FavoritePrefsManager(context: Context) {

    private val prefs = context.getSharedPreferences(Constants.PREFS_FILE_NAME, Context.MODE_PRIVATE)

    fun getFavorites(): Set<String> {
        return prefs.getStringSet(Constants.FAVORITES_KEY_NAME, emptySet()) ?: emptySet()
    }

    fun isFavorite(recipeId: Int): Boolean {
        return getFavorites().contains(recipeId.toString())
    }

    fun addFavorite(recipeId: Int) {
        val updatedFavorites = getFavorites().toMutableSet()
        updatedFavorites.add(recipeId.toString())

        prefs.edit { putStringSet(Constants.FAVORITES_KEY_NAME, updatedFavorites) }
    }

    fun removeFavorite(recipeId: Int) {
        val updatedFavorites = getFavorites().toMutableSet()
        updatedFavorites.remove(recipeId.toString())

        prefs.edit { putStringSet(Constants.FAVORITES_KEY_NAME, updatedFavorites) }
    }
}