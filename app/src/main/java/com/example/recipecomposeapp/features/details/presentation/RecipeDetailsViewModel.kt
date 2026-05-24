package com.example.recipecomposeapp.features.details.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipecomposeapp.core.ui.navigation.Destination
import com.example.recipecomposeapp.core.utils.FavoriteDataStoreManager
import com.example.recipecomposeapp.data.repository.RecipesRepository
import com.example.recipecomposeapp.features.details.presentation.model.RecipeDetailsUiState
import com.example.recipecomposeapp.features.recipes.presentation.model.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(
    application: Application,
    model: Destination.RecipeDetails,
    private val repository: RecipesRepository
) : AndroidViewModel(application) {

    private val favoriteManager = FavoriteDataStoreManager(application)

    private val _uiState = MutableStateFlow(RecipeDetailsUiState(isLoading = true))

    val uiState: StateFlow<RecipeDetailsUiState> = _uiState.asStateFlow()

    init {
        observeRecipe(model.recipeId)
        observeFavoriteState(model.recipeId)
    }

    private fun observeRecipe(recipeId: Int) {
        viewModelScope.launch {
            repository.getRecipe(recipeId)
                .catch { e ->
                    _uiState.update {
                        it.copy(isLoading = false, error = e.localizedMessage ?: "Ошибка загрузки")
                    }
                }
                .collect { dto ->
                    if (dto != null) {
                        _uiState.update {
                            it.copy(isLoading = false, recipe = dto.toUiModel(), error = null)
                        }
                    } else {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                }
        }
    }

    private fun observeFavoriteState(recipeId: Int) {
        favoriteManager.isFavoriteFlow(recipeId)
            .onEach { isFavorite ->
                _uiState.update { it.copy(isFavorite = isFavorite) }
            }
            .launchIn(viewModelScope)
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