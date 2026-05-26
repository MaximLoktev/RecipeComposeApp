package com.example.recipecomposeapp.features.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipecomposeapp.core.utils.FavoriteDataStoreManager
import com.example.recipecomposeapp.data.repository.RecipesRepository
import com.example.recipecomposeapp.features.favorites.presentation.model.FavoritesUiState
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import com.example.recipecomposeapp.features.recipes.presentation.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoriteManager: FavoriteDataStoreManager,
    private val repository: RecipesRepository
) : ViewModel() {

    val uiState: StateFlow<FavoritesUiState> = favoriteManager
        .getFavoriteIdsFlow()
        .map { idsSet ->
            val recipes = mutableListOf<RecipeUiModel>()

            idsSet.forEach { idString ->
                idString.toIntOrNull()?.let { id ->
                    runCatching { repository.getRecipe(id).firstOrNull()?.toUiModel() }
                        .getOrNull()
                        ?.let { recipes.add(it) }
                }
            }

            FavoritesUiState(favoriteRecipes = recipes, isLoading = false)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FavoritesUiState(isLoading = true)
        )
}