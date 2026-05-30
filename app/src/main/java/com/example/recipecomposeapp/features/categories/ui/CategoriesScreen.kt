package com.example.recipecomposeapp.features.categories.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.core.ui.components.ScreenHeader
import com.example.recipecomposeapp.core.ui.theme.Dimens
import com.example.recipecomposeapp.features.categories.presentation.CategoriesViewModel
import com.example.recipecomposeapp.features.categories.presentation.model.CategoriesUiState

@Composable
fun CategoriesScreen(
    onCategoryClick: (Int, String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CategoriesViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    CategoriesContent(
        uiState = uiState,
        onCategoryClick = onCategoryClick,
        onRetryClick = { viewModel.retry() },
        modifier = modifier
    )
}

@Composable
fun CategoriesContent(
    uiState: CategoriesUiState,
    onCategoryClick: (Int, String, String) -> Unit,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val screenTitle = stringResource(R.string.categories)

    Column(modifier = modifier.fillMaxSize()) {
        ScreenHeader(
            imageModel = R.drawable.bcg_categories,
            contentDescription = screenTitle,
            text = screenTitle.uppercase()
        )

        Box(
            modifier = Modifier.fillMaxSize(),
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
                uiState.isLoading && uiState.categories.isEmpty() -> {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.testTag("loading_indicator")
                    )
                }
                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            horizontal = Dimens.paddingLarge,
                            vertical = Dimens.paddingLarge
                        ),
                        horizontalArrangement = Arrangement.spacedBy(Dimens.paddingLarge),
                        verticalArrangement = Arrangement.spacedBy(Dimens.paddingLarge)
                    ) {
                        items(
                            items = uiState.categories,
                            key = { category -> category.id }
                        ) { category ->
                            CategoryItem(
                                category = category,
                                onClick = {
                                    onCategoryClick(category.id, category.title, category.imageUrl)
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