package com.example.recipecomposeapp.ui.recipes.model

import androidx.compose.runtime.Immutable
import com.example.recipecomposeapp.data.model.IngredientDto

@Immutable
data class IngredientUiModel(
    val quantity: String,
    val unitOfMeasure: String,
    val name: String,
)

fun IngredientDto.toUiModel() = IngredientUiModel(
    quantity = quantity,
    unitOfMeasure = unitOfMeasure,
    name = description,
)