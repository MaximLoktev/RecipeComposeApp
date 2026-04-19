package com.example.recipecomposeapp

import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.example.recipecomposeapp.Constants.DEEP_LINK_BASE_URL
import com.example.recipecomposeapp.Constants.DEEP_LINK_SCHEME
import com.example.recipecomposeapp.data.repository.RecipesRepositoryStub
import com.example.recipecomposeapp.ui.categories.CategoriesScreen
import com.example.recipecomposeapp.ui.details.RecipeDetailsScreen
import com.example.recipecomposeapp.ui.favorites.FavoritesScreen
import com.example.recipecomposeapp.ui.navigation.BottomNavigation
import com.example.recipecomposeapp.ui.navigation.Destination
import com.example.recipecomposeapp.ui.recipes.RecipesScreen
import com.example.recipecomposeapp.ui.recipes.model.toUiModel
import com.example.recipecomposeapp.ui.theme.RecipeComposeAppTheme
import kotlinx.coroutines.delay

@Composable
fun RecipesApp(externalIntent: Intent? = null) {

    val navController = rememberNavController()

    LaunchedEffect(externalIntent) {
        externalIntent?.let {
            delay(100)
            navController.handleDeepLink(it)
        }
    }

    RecipeComposeAppTheme {
        Scaffold(
            bottomBar = {
                BottomNavigation(
                    onCategoriesClick = {
                        navController.navigate(Destination.Categories) {
                            popUpTo(navController.graph.findStartDestination().id)
                        }
                    },
                    onFavoriteClick = {
                        navController.navigate(Destination.Favorites) {
                            popUpTo(navController.graph.findStartDestination().id)
                        }
                    }
                )
            }
        ) { paddingValues ->

            NavHost(
                navController = navController,
                startDestination = Destination.Categories,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable<Destination.Categories> {
                    CategoriesScreen { categoryId, categoryTitle ->
                        navController.navigate(Destination.Recipes(categoryId, categoryTitle))
                    }
                }
                composable<Destination.Favorites> {
                    FavoritesScreen()
                }
                composable<Destination.Recipes> { backStackEntry ->
                    val args = backStackEntry.toRoute<Destination.Recipes>()

                    RecipesScreen(
                        categoryId = args.categoryId,
                        categoryTitle = args.categoryTitle,
                        onRecipeClick = { recipeId, _ ->
                            navController.navigate(Destination.RecipeDetails(recipeId))
                        },
                        onBackClick = { navController.popBackStack() }
                    )
                }
                composable<Destination.RecipeDetails>(
                    deepLinks = listOf(
                        navDeepLink<Destination.RecipeDetails>(
                            basePath = "$DEEP_LINK_SCHEME://recipe"
                        ),
                        navDeepLink<Destination.RecipeDetails>(
                            basePath = "$DEEP_LINK_BASE_URL/recipe"
                        )
                    )
                ) { backStackEntry ->
                    val args = backStackEntry.toRoute<Destination.RecipeDetails>()
                    val recipe = RecipesRepositoryStub.getRecipeById(args.recipeId)?.toUiModel()

                    var isFavorite by rememberSaveable { mutableStateOf(false) }

                    RecipeDetailsScreen(
                        recipeId = args.recipeId,
                        initialRecipe = recipe,
                        onBackClick = { navController.popBackStack() },
                        isFavorite = isFavorite,
                        onFavoriteToggle = { isFavorite = !isFavorite }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipesAppPreview() {
    RecipeComposeAppTheme {
        RecipesApp()
    }
}