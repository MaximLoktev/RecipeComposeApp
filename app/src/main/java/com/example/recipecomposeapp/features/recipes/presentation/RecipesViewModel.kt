package com.example.recipecomposeapp.features.recipes.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.recipecomposeapp.core.ui.navigation.Destination
import com.example.recipecomposeapp.data.repository.RecipesRepository
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipesUiState
import com.example.recipecomposeapp.features.recipes.presentation.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: RecipesRepository
) : ViewModel() {

    private val model = savedStateHandle.toRoute<Destination.Recipes>()

    private val _uiState = MutableStateFlow(
        RecipesUiState(
            categoryTitle = model.categoryTitle,
            categoryImageUrl = model.categoryImageUrl,
            isLoading = true
        )
    )

    val uiState: StateFlow<RecipesUiState> = _uiState.asStateFlow()

    init {
        observeRecipes(model.categoryId)
    }

    fun retry() {
        _uiState.update {
            it.copy(isLoading = true, isError = false)
        }
        observeRecipes(model.categoryId)
    }

    private fun observeRecipes(categoryId: Int) {
        viewModelScope.launch {
            repository.getRecipesByCategory(categoryId)
                .map { dto -> dto.map { it.toUiModel() } }
                .catch {
                    _uiState.update { state ->
                        state.copy(isLoading = false, isError = true)
                    }
                }
                .collect { recipesList ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            isError = false,
                            recipes = recipesList
                        )
                    }
                }
        }
    }
}