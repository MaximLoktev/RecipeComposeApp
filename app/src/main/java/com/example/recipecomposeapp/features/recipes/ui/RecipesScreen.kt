package com.example.recipecomposeapp.features.recipes.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.example.recipecomposeapp.features.categories.presentation.model.toUiModel
import com.example.recipecomposeapp.core.ui.components.ScreenHeader
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import com.example.recipecomposeapp.features.recipes.presentation.model.toUiModel
import com.example.recipecomposeapp.core.ui.theme.Dimens

@Composable
fun RecipesScreen(
    categoryId: Int,
    categoryTitle: String,
    modifier: Modifier = Modifier,
    onRecipeClick: (Int, RecipeUiModel) -> Unit,
    onBackClick: () -> Unit,
) {

    var categoryImageUrl by remember { mutableStateOf("") }

    var recipes by remember { mutableStateOf(emptyList<RecipeUiModel>()) }

    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(categoryId) {
        isLoading = true

        try {
            val category = RecipesRepositoryStub
                .getCategories()
                .find { it.id == categoryId }
                ?.toUiModel()

            categoryImageUrl = category?.imageUrl ?: ""

            recipes = RecipesRepositoryStub
                .getRecipesByCategoryId(categoryId)
                .map { it.toUiModel() }
        } finally {
            isLoading = false
        }
    }

    Column(modifier = modifier.fillMaxSize()) {

        val headerPainter = if (categoryImageUrl.isNotEmpty()) {
            rememberAsyncImagePainter(model = categoryImageUrl)
        } else {
            painterResource(id = R.drawable.img_placeholder)
        }

        ScreenHeader(
            painter = headerPainter,
            contentDescription = categoryTitle,
            text = categoryTitle.uppercase(),
            onBackClick = onBackClick,
        )

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.Blue)
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(Dimens.paddingLarge),
                verticalArrangement = Arrangement.spacedBy(Dimens.paddingLarge),
            ) {
                items(
                    items = recipes,
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