package com.example.recipecomposeapp.app.di

import android.app.Application
import com.example.recipecomposeapp.core.ui.navigation.Destination
import com.example.recipecomposeapp.data.repository.RecipesRepository
import com.example.recipecomposeapp.features.details.presentation.RecipeDetailsViewModel

class RecipeDetailsViewModelFactory(
    private val application: Application,
    private val model: Destination.RecipeDetails,
    private val repository: RecipesRepository
) : Factory<RecipeDetailsViewModel> {

    override fun create(): RecipeDetailsViewModel =
        RecipeDetailsViewModel(application, model, repository)
}