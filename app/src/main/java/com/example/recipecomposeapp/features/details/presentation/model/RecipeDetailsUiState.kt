package com.example.recipecomposeapp.features.details.presentation.model

import com.example.recipecomposeapp.features.recipes.presentation.model.IngredientUiModel
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import kotlin.math.roundToInt

private const val DEFAULT_PORTIONS = 4

data class RecipeDetailsUiState(
    val recipe: RecipeUiModel? = null,
    val portionsCount: Int = DEFAULT_PORTIONS,
    val isFavorite: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
) {

    val computedIngredients: List<IngredientUiModel>
        get() = recipe?.ingredients?.map {
            val multiplier = portionsCount.toDouble() / DEFAULT_PORTIONS.toDouble()

            val baseAmount = it.quantity.toDoubleOrNull() ?: 0.0

            val formattedQuantity = if (baseAmount > 0) {
                formatQuantity(baseAmount, multiplier)
            } else {
                it.quantity
            }

            it.copy(quantity = formattedQuantity)
        } ?: emptyList()

    /**
     * Умножает базовое количество на множитель порций и красиво форматирует результат.
     * Оставляет один знак после запятой, но убирает ".0", если число целое.
     */
    private fun formatQuantity(baseAmount: Double, multiplier: Double): String {
        val scaledAmount = baseAmount * multiplier
        val rounded = (scaledAmount * 10).roundToInt() / 10.0

        return if (rounded % 1.0 == 0.0) {
            rounded.toInt().toString()
        } else {
            rounded.toString()
        }
    }
}