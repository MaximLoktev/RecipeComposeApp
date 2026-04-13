package com.example.recipecomposeapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.recipecomposeapp.ui.categories.CategoriesScreen
import com.example.recipecomposeapp.ui.details.RecipeDetailsScreen
import com.example.recipecomposeapp.ui.favorites.FavoritesScreen
import com.example.recipecomposeapp.ui.navigation.BottomNavigation
import com.example.recipecomposeapp.ui.navigation.Destination
import com.example.recipecomposeapp.ui.navigation.KEY_RECIPE_OBJECT
import com.example.recipecomposeapp.ui.recipes.RecipesScreen
import com.example.recipecomposeapp.ui.recipes.model.RecipeUiModel
import com.example.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun RecipesApp() {

    val navController = rememberNavController()

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
                        onRecipeClick = { recipeId, recipe ->
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                KEY_RECIPE_OBJECT, recipe
                            )
                            navController.navigate(Destination.RecipeDetails(recipeId))
                        },
                        onBackClick = { navController.popBackStack() }
                    )
                }
                composable<Destination.RecipeDetails> { backStackEntry ->
                    val args = backStackEntry.toRoute<Destination.RecipeDetails>()

                    val recipe = remember {
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.get<RecipeUiModel>(KEY_RECIPE_OBJECT)
                    }

                    RecipeDetailsScreen(
                        recipeId = args.recipeId,
                        initialRecipe = recipe,
                        onBackClick = { navController.popBackStack() }
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