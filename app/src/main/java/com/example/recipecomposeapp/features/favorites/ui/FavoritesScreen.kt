package com.example.recipecomposeapp.features.favorites.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.example.recipecomposeapp.core.ui.components.ScreenHeader
import com.example.recipecomposeapp.features.recipes.ui.RecipeItem
import com.example.recipecomposeapp.features.recipes.presentation.model.toUiModel
import com.example.recipecomposeapp.core.ui.theme.Dimens
import com.example.recipecomposeapp.core.utils.FavoriteDataStoreManager
import kotlinx.coroutines.flow.map

@Composable
fun FavoritesScreen(
    recipesRepository: RecipesRepositoryStub,
    favoriteManager: FavoriteDataStoreManager,
    onRecipeClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    val favoriteRecipesFlow = remember(favoriteManager, recipesRepository) {
        favoriteManager.getFavoriteIdsFlow().map { idsSet ->
            idsSet.mapNotNull { idString ->
                val id = idString.toIntOrNull()
                id?.let { recipesRepository.getRecipeById(it)?.toUiModel() }
            }
        }
    }

    val favoriteRecipes by favoriteRecipesFlow.collectAsState(initial = emptyList())

    val screenTitle = stringResource(R.string.favorites)

    Column(modifier = modifier.fillMaxSize()) {
        ScreenHeader(
            painterResource(id = R.drawable.bcg_favorites),
            contentDescription = screenTitle,
            text = screenTitle.uppercase()
        )

        if (favoriteRecipes.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Dimens.paddingLarge),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.favorites_empty_state),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(Dimens.paddingLarge),
                verticalArrangement = Arrangement.spacedBy(Dimens.paddingLarge),
            ) {
                items(
                    items = favoriteRecipes,
                    key = { recipe -> recipe.id }
                ) { recipe ->
                    RecipeItem(
                        recipe = recipe,
                        onClick = { recipeId ->
                            onRecipeClick(recipeId)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}