package com.example.recipecomposeapp.features.recipes.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.core.ui.components.ScreenHeader
import com.example.recipecomposeapp.core.ui.theme.Dimens
import com.example.recipecomposeapp.features.recipes.presentation.RecipesViewModel
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipesUiState

@Composable
fun RecipesScreen(
    viewModel: RecipesViewModel,
    onRecipeClick: (Int, RecipeUiModel) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val uiState by viewModel.uiState.collectAsState()

    RecipesContent(
        uiState = uiState,
        onRecipeClick = onRecipeClick,
        onBackClick = onBackClick,
        onRetryClick = { viewModel.retry() },
        modifier = modifier
    )
}

@Composable
fun RecipesContent(
    uiState: RecipesUiState,
    onRecipeClick: (Int, RecipeUiModel) -> Unit,
    onBackClick: () -> Unit,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize().testTag("recipes_screen")) {
        ScreenHeader(
            imageModel = uiState.categoryImageUrl,
            contentDescription = uiState.categoryTitle,
            text = uiState.categoryTitle.uppercase(),
            onBackClick = onBackClick,
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isError -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = stringResource(R.string.error_loading_data),
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.testTag("error_message")
                        )

                        Spacer(modifier = Modifier.height(Dimens.paddingLarge))

                        Button(onClick = onRetryClick) {
                            Text(text = stringResource(R.string.retry))
                        }
                    }
                }
                uiState.isLoading && uiState.recipes.isEmpty() -> {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.testTag("loading_indicator")
                    )
                }
                uiState.isEmpty -> {
                    Text(
                        text = stringResource(R.string.empty_category_message),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.testTag("empty_state")
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(Dimens.paddingLarge),
                        verticalArrangement = Arrangement.spacedBy(Dimens.paddingLarge),
                    ) {
                        items(
                            items = uiState.recipes,
                            key = { recipe -> recipe.id }
                        ) { recipe ->
                            RecipeItem(
                                recipe = recipe,
                                onClick = { recipeId ->
                                    onRecipeClick(recipeId, recipe)
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}