package com.example.recipecomposeapp.features.details.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipecomposeapp.core.utils.FavoriteDataStoreManager
import com.example.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.example.recipecomposeapp.features.details.presentation.model.RecipeDetailsUiState
import com.example.recipecomposeapp.features.recipes.presentation.model.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val favoriteManager = FavoriteDataStoreManager(application)

    private val _uiState = MutableStateFlow(RecipeDetailsUiState())

    val uiState: StateFlow<RecipeDetailsUiState> = _uiState.asStateFlow()

    fun loadRecipe(recipeId: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, error = null)
            }

            try {
                val loadedRecipe = RecipesRepositoryStub.getRecipeById(recipeId)?.toUiModel()

                if (loadedRecipe != null) {
                    _uiState.update {
                        it.copy(isLoading = false, recipe = loadedRecipe)
                    }

                    favoriteManager.isFavoriteFlow(recipeId)
                        .onEach { isFavorite ->
                            _uiState.update {
                                it.copy(isFavorite = isFavorite)
                            }
                        }
                        .launchIn(viewModelScope)
                } else {
                    _uiState.update {
                        it.copy(isLoading = false, recipe = null)
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = e.localizedMessage ?: "Ошибка загрузки рецепта")
                }
            }
        }
    }

    fun toggleFavorite() {
        val currentRecipe = _uiState.value.recipe ?: return
        val currentlyFavorite = _uiState.value.isFavorite

        viewModelScope.launch {
            if (currentlyFavorite) {
                favoriteManager.removeFavorite(currentRecipe.id)
            } else {
                favoriteManager.addFavorite(currentRecipe.id)
            }
        }
    }

    fun updatePortions(newCount: Int) {
        if (newCount > 0) {
            _uiState.update { it.copy(portionsCount = newCount) }
        }
    }
}