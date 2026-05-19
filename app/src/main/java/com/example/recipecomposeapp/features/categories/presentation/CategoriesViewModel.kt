package com.example.recipecomposeapp.features.categories.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipecomposeapp.data.repository.RecipesRepository
import com.example.recipecomposeapp.features.categories.presentation.model.CategoriesUiState
import com.example.recipecomposeapp.features.categories.presentation.model.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val repository: RecipesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoriesUiState(isLoading = true))

    val uiState: StateFlow<CategoriesUiState> = _uiState.asStateFlow()

    init {
        observeCategories()
    }

    private fun observeCategories() {
        viewModelScope.launch {
            repository.getCategories()
                .map { dto -> dto.map { it.toUiModel() } }
                .collect { categoriesList ->
                    _uiState.update { state ->
                        state.copy(isLoading = false, categories = categoriesList)
                    }
                }
        }
    }
}