package com.example.recipecomposeapp.features.categories.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.app.di.CategoriesViewModelFactory
import com.example.recipecomposeapp.app.di.RecipeApplication
import com.example.recipecomposeapp.core.ui.components.ScreenHeader
import com.example.recipecomposeapp.core.ui.theme.Dimens

@Composable
fun CategoriesScreen(
    onCategoryClick: (Int, String, String) -> Unit,
    modifier: Modifier = Modifier,
) {

    val context = LocalContext.current
    val application = context.applicationContext as? RecipeApplication ?: return
    val appContainer = application.appContainer

    val viewModel = remember {
        CategoriesViewModelFactory(appContainer.recipesRepository).create()
    }

    val uiState by viewModel.uiState.collectAsState()

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
            if (uiState.isLoading && uiState.categories.isEmpty()) {
                CircularProgressIndicator()
            } else {
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