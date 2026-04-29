package com.example.recipecomposeapp.features.recipes.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.core.ui.components.ScreenHeader
import com.example.recipecomposeapp.core.ui.theme.Dimens
import com.example.recipecomposeapp.features.recipes.presentation.RecipesViewModel
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel

@Composable
fun RecipesScreen(
    onRecipeClick: (Int, RecipeUiModel) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val viewModel: RecipesViewModel = viewModel()

    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {
        val headerPainter = if (uiState.categoryImageUrl.isNotEmpty()) {
            rememberAsyncImagePainter(model = uiState.categoryImageUrl)
        } else {
            painterResource(id = R.drawable.img_placeholder)
        }

        ScreenHeader(
            painter = headerPainter,
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
                uiState.isLoading -> {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
                uiState.error != null -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(Dimens.paddingSmall),
                        modifier = Modifier.padding(Dimens.paddingLarge)
                    ) {
                        Text(
                            text = uiState.error.orEmpty(),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Button(
                            onClick = { viewModel.retry() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.tertiary,
                                contentColor = MaterialTheme.colorScheme.onTertiary
                            ),
                        ) {
                            Text(
                                text = stringResource(R.string.retry_attempt),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
                uiState.isEmpty -> {
                    Text(
                        text = stringResource(R.string.empty_category_message),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
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