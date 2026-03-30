package com.example.recipecomposeapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipecomposeapp.ui.categories.CategoriesScreen
import com.example.recipecomposeapp.ui.favorites.FavoritesScreen
import com.example.recipecomposeapp.ui.navigation.BottomNavigation
import com.example.recipecomposeapp.ui.recipes.RecipesScreen
import com.example.recipecomposeapp.ui.theme.RecipeComposeAppTheme

enum class ScreenId {
    CATEGORIES,
    FAVORITES,
    RECIPES,
}

@Preview(showBackground = true)
@Composable
fun RecipesApp() {
    var currentScreen by remember { mutableStateOf(ScreenId.CATEGORIES) }

    var selectedCategoryId by remember { mutableStateOf<Int?>(null) }
    var selectedCategoryTitle by remember { mutableStateOf("") }

    RecipeComposeAppTheme {
        Scaffold(
            bottomBar = {
                BottomNavigation(
                    onCategoriesClick = { currentScreen = ScreenId.CATEGORIES },
                    onFavoriteClick = { currentScreen = ScreenId.FAVORITES }
                )
            }
        ) { paddingValues ->
            val modifier = Modifier.padding(paddingValues)

            when (currentScreen) {
                ScreenId.CATEGORIES -> {
                    CategoriesScreen(modifier) { categoryId, categoryTitle ->
                        selectedCategoryId = categoryId
                        selectedCategoryTitle = categoryTitle
                        currentScreen = ScreenId.RECIPES
                    }
                }
                ScreenId.FAVORITES -> {
                    FavoritesScreen(modifier)
                }
                ScreenId.RECIPES -> {
                    RecipesScreen(
                        categoryId = selectedCategoryId ?: error("Category ID is required"),
                        categoryTitle = selectedCategoryTitle,
                        onRecipeClick = { recipeId ->
                            //TODO: реализовать переход к выбранному рецепту
                        },
//                        onBackClick = {
//                            currentScreen = ScreenId.CATEGORIES
//                        },
                        modifier = modifier
                    )
                }
            }
        }
    }
}