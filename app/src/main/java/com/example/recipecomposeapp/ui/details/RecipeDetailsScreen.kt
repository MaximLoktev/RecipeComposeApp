package com.example.recipecomposeapp.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.compose.rememberAsyncImagePainter
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.ui.components.ScreenHeader
import com.example.recipecomposeapp.ui.navigation.ShareUtils
import com.example.recipecomposeapp.ui.recipes.model.RecipeUiModel
import com.example.recipecomposeapp.ui.theme.Dimens
import kotlin.math.roundToInt

private const val DEFAULT_PORTIONS = 4
private const val MIN_PORTIONS = 1f
private const val MAX_PORTIONS = 12f
private const val SLIDER_STEPS = 10

@Composable
fun RecipeDetailsScreen(
    recipeId: Int,
    initialRecipe: RecipeUiModel?,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {

    val context = LocalContext.current

    var currentPortions by remember { mutableIntStateOf(DEFAULT_PORTIONS) }

    if (initialRecipe == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Рецепт не найден", style = MaterialTheme.typography.titleMedium)
        }
    } else {
        val scaledIngredients = remember(currentPortions, initialRecipe) {
            val multiplier = currentPortions.toDouble() / DEFAULT_PORTIONS.toDouble()

            initialRecipe.ingredients.map { ingredient ->
                val baseAmount = ingredient.quantity.toDoubleOrNull() ?: 0.0

                val formattedQuantity = if (baseAmount > 0) {
                    formatQuantity(baseAmount, multiplier)
                } else {
                    ingredient.quantity
                }

                ingredient.copy(quantity = formattedQuantity)
            }
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
        ) {
            ScreenHeader(
                painter = rememberAsyncImagePainter(
                    model = initialRecipe.imageUrl.ifEmpty { R.drawable.bcg_categories }
                ),
                contentDescription = initialRecipe.title,
                text = initialRecipe.title.uppercase(),
                onBackClick = onBackClick,
                showShareButton = true,
                onShareClick = {
                    ShareUtils.shareRecipe(
                        context = context,
                        recipeId = initialRecipe.id,
                        recipeTitle = initialRecipe.title
                    )
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.paddingLarge)
            ) {
                Text(
                    text = stringResource(R.string.ingredients).uppercase(),
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                val portionsTitle = stringResource(R.string.portions)

                Text(
                    text = "$portionsTitle: $currentPortions",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = Dimens.paddingSmall)
                )

                Slider(
                    value = currentPortions.toFloat(),
                    onValueChange = { currentPortions = it.roundToInt() },
                    valueRange = MIN_PORTIONS..MAX_PORTIONS,
                    steps = SLIDER_STEPS,
                    colors = SliderDefaults.colors(
                        thumbColor = MaterialTheme.colorScheme.tertiary,
                        activeTrackColor = MaterialTheme.colorScheme.tertiary,
                        inactiveTrackColor = MaterialTheme.colorScheme.tertiaryContainer
                    ),
                    modifier = Modifier.padding(vertical = Dimens.paddingSmall)
                )

                Surface(
                    shape = RoundedCornerShape(Dimens.buttonCornerRadius),
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(
                            horizontal = Dimens.paddingMedium,
                            vertical = Dimens.paddingExtraSmall
                        )
                    ) {
                        scaledIngredients.forEachIndexed { index, ingredient ->
                            IngredientItem(ingredient = ingredient)

                            if (index < scaledIngredients.lastIndex) {
                                HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(Dimens.paddingLarge))

                if (initialRecipe.method.isNotEmpty()) {
                    Text(
                        text = stringResource(R.string.cooking_method).uppercase(),
                        style = MaterialTheme.typography.displayLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = Dimens.paddingLarge)
                    )

                    Surface(
                        shape = RoundedCornerShape(Dimens.buttonCornerRadius),
                        color = MaterialTheme.colorScheme.surface,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(
                                horizontal = Dimens.paddingMedium,
                                vertical = Dimens.paddingExtraSmall
                            )
                        ) {
                            initialRecipe.method.forEachIndexed { index, step ->
                                InstructionItem(stepNumber = index + 1, instruction = step)

                                if (index < initialRecipe.method.lastIndex) {
                                    HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

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