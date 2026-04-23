package com.example.recipecomposeapp.ui.utils

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first

class FavoriteDataStoreManager(private val context: Context) {

    private fun Preferences.getFavorites(): Set<String> {
        return this[PreferencesKeys.FAVORITE_RECIPE_IDS] ?: emptySet()
    }

    suspend fun isFavorite(recipeId: Int): Boolean {
        val preferences = context.dataStore.data.first()

        return preferences.getFavorites().contains(recipeId.toString())
    }

    suspend fun addFavorite(recipeId: Int) {
        context.dataStore.edit { preferences ->
            val updatedFavorites = preferences.getFavorites() + recipeId.toString()

            preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] = updatedFavorites
        }
    }

    suspend fun removeFavorite(recipeId: Int) {
        context.dataStore.edit { preferences ->
            val updatedFavorites = preferences.getFavorites() - recipeId.toString()

            preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] = updatedFavorites
        }
    }
}