package com.example.recipecomposeapp.ui.utils

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteDataStoreManager(private val context: Context) {

    private fun Preferences.getFavorites(): Set<String> =
        this[PreferencesKeys.FAVORITE_RECIPE_IDS] ?: emptySet()

    fun getFavoriteIdsFlow(): Flow<Set<String>> =
        context.dataStore.data.map { it.getFavorites() }

    fun getFavoriteCountFlow(): Flow<Int> =
        getFavoriteIdsFlow().map { it.size }

    fun isFavoriteFlow(recipeId: Int): Flow<Boolean> =
        getFavoriteIdsFlow().map { it.contains(recipeId.toString()) }

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