package com.example.recipecomposeapp.ui.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.example.recipecomposeapp.ui.categories.model.toUiModel
import com.example.recipecomposeapp.ui.components.ScreenHeader
import com.example.recipecomposeapp.ui.theme.Dimens

@Composable
fun CategoriesScreen(modifier: Modifier = Modifier, onCategoryClick: (Int) -> Unit) {

    val screenTitle = stringResource(R.string.categories)

    val categories = RecipesRepositoryStub
        .getCategories()
        .map { it.toUiModel() }

    Column(modifier = modifier.fillMaxSize()) {
        ScreenHeader(
            painterResource(id = R.drawable.bcg_categories),
            contentDescription = screenTitle,
            text = screenTitle.uppercase()
        )

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
                items = categories,
                key = { category -> category.id }
            ) { category ->
                CategoryItem(
                    category = category,
                    onClick = { onCategoryClick(category.id) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}