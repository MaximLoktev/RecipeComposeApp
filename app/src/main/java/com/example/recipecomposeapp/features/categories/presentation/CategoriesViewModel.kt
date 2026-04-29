package com.example.recipecomposeapp.features.categories.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.example.recipecomposeapp.features.categories.presentation.model.CategoriesUiState
import com.example.recipecomposeapp.features.categories.presentation.model.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoriesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CategoriesUiState())

    val uiState: StateFlow<CategoriesUiState> = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, error = null)
            }

            try {
                val loadedCategories = RecipesRepositoryStub
                    .getCategories()
                    .map { it.toUiModel() }

                _uiState.update {
                    it.copy(isLoading = false, categories = loadedCategories)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = e.localizedMessage ?: "Произошла неизвестная ошибка")
                }
            }
        }
    }

    fun retryLoading() {
        loadCategories()
    }
}