package com.example.recipecomposeapp.features.favorites.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipecomposeapp.core.utils.FavoriteDataStoreManager
import com.example.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.example.recipecomposeapp.features.favorites.presentation.model.FavoritesUiState
import com.example.recipecomposeapp.features.recipes.presentation.model.toUiModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val favoriteManager = FavoriteDataStoreManager(application)

    val uiState: StateFlow<FavoritesUiState> = favoriteManager
        .getFavoriteIdsFlow()
        .map { idsSet ->
            val recipes = idsSet.mapNotNull { idString ->
                val id = idString.toIntOrNull()
                id?.let { RecipesRepositoryStub.getRecipeById(it)?.toUiModel() }
            }
            FavoritesUiState(favoriteRecipes = recipes, isLoading = false)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FavoritesUiState(isLoading = true)
        )
}