package com.example.recipecomposeapp.features.details.ui

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.core.ui.components.ScreenHeader
import com.example.recipecomposeapp.core.ui.theme.Dimens
import com.example.recipecomposeapp.core.utils.ShareUtils
import com.example.recipecomposeapp.features.details.presentation.RecipeDetailsViewModel
import com.example.recipecomposeapp.features.recipes.presentation.model.IngredientUiModel
import kotlin.math.roundToInt

private const val MIN_PORTIONS = 1f
private const val MAX_PORTIONS = 12f
private const val SLIDER_STEPS = 10

@Composable
fun RecipeDetailsScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecipeDetailsViewModel = viewModel(),
) {

    val context = LocalContext.current

    val uiState by viewModel.uiState.collectAsState()

    when {
        uiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }
        uiState.error != null -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(Dimens.paddingLarge),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = uiState.error.orEmpty(),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        uiState.recipe == null -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.recipe_not_found),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
        else -> {
            val currentRecipe = uiState.recipe!!

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState())
            ) {
                ScreenHeader(
                    painter = rememberAsyncImagePainter(
                        model = currentRecipe.imageUrl.ifEmpty { R.drawable.bcg_categories }
                    ),
                    contentDescription = currentRecipe.title,
                    text = currentRecipe.title.uppercase(),
                    onBackClick = onBackClick,
                    showShareButton = true,
                    onShareClick = {
                        ShareUtils.shareRecipe(
                            context = context,
                            recipeId = currentRecipe.id,
                            recipeTitle = currentRecipe.title
                        )
                    },
                    showFavoriteButton = true,
                    isFavorite = uiState.isFavorite,
                    onFavoriteToggle = { viewModel.toggleFavorite() },
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.paddingLarge)
                ) {
                    IngredientsSection(
                        ingredients = uiState.computedIngredients,
                        portionsCount = uiState.portionsCount,
                        onPortionsChange = { viewModel.updatePortions(it) },
                    )

                    if (currentRecipe.method.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(Dimens.paddingLarge))

                        CookingMethodSection(method = currentRecipe.method)
                    }
                }
            }
        }
    }
}

@Composable
private fun IngredientsSection(
    ingredients: List<IngredientUiModel>,
    portionsCount: Int,
    onPortionsChange: (Int) -> Unit
) {
    Text(
        text = stringResource(R.string.ingredients).uppercase(),
        style = MaterialTheme.typography.displayLarge,
        color = MaterialTheme.colorScheme.primary
    )

    Text(
        text = "${stringResource(R.string.portions)}: $portionsCount",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(top = Dimens.paddingSmall)
    )

    Slider(
        value = portionsCount.toFloat(),
        onValueChange = { onPortionsChange(it.roundToInt()) },
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
            ingredients.forEachIndexed { index, ingredient ->
                IngredientItem(ingredient = ingredient)

                if (index < ingredients.lastIndex) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                }
            }
        }
    }
}

@Composable
private fun CookingMethodSection(method: List<String>) {
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
            method.forEachIndexed { index, step ->
                InstructionItem(stepNumber = index + 1, instruction = step)

                if (index < method.lastIndex) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                }
            }
        }
    }
}