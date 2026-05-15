package com.example.recipecomposeapp.features.recipes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipecomposeapp.core.ui.navigation.Destination
import com.example.recipecomposeapp.data.repository.RecipesRepository
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipesUiState
import com.example.recipecomposeapp.features.recipes.presentation.model.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipesViewModel(
    private val model: Destination.Recipes,
    private val repository: RecipesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        RecipesUiState(
            categoryTitle = model.categoryTitle,
            categoryImageUrl = model.categoryImageUrl
        )
    )

    val uiState: StateFlow<RecipesUiState> = _uiState.asStateFlow()

    init {
        loadRecipes(model.categoryId)
    }

    private fun loadRecipes(categoryId: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, error = null)
            }

            try {
                val loadedRecipes = repository
                    .getRecipesByCategory(categoryId)
                    .map { it.toUiModel() }

                _uiState.update {
                    it.copy(isLoading = false, recipes = loadedRecipes)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = e.localizedMessage ?: "Ошибка загрузки")
                }
            }
        }
    }

    fun retry() {
        loadRecipes(model.categoryId)
    }
}