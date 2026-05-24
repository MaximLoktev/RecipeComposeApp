package com.example.recipecomposeapp.app.di

import com.example.recipecomposeapp.core.ui.navigation.Destination
import com.example.recipecomposeapp.data.repository.RecipesRepository
import com.example.recipecomposeapp.features.recipes.presentation.RecipesViewModel

class RecipesViewModelFactory(
    private val model: Destination.Recipes,
    private val repository: RecipesRepository
) : Factory<RecipesViewModel> {

    override fun create(): RecipesViewModel =
        RecipesViewModel(model, repository)
}