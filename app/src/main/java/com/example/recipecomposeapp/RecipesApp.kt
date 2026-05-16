package com.example.recipecomposeapp

import android.app.Application
import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.example.recipecomposeapp.core.Constants.DEEP_LINK_BASE_URL
import com.example.recipecomposeapp.core.Constants.DEEP_LINK_SCHEME
import com.example.recipecomposeapp.core.network.NetworkConfig
import com.example.recipecomposeapp.core.network.api.RecipesApiService
import com.example.recipecomposeapp.core.ui.navigation.BottomNavigation
import com.example.recipecomposeapp.core.ui.navigation.Destination
import com.example.recipecomposeapp.core.ui.theme.RecipeComposeAppTheme
import com.example.recipecomposeapp.core.utils.FavoriteDataStoreManager
import com.example.recipecomposeapp.data.repository.RecipesRepositoryImpl
import com.example.recipecomposeapp.features.categories.presentation.CategoriesViewModel
import com.example.recipecomposeapp.features.categories.ui.CategoriesScreen
import com.example.recipecomposeapp.features.details.presentation.RecipeDetailsViewModel
import com.example.recipecomposeapp.features.details.ui.RecipeDetailsScreen
import com.example.recipecomposeapp.features.favorites.presentation.FavoritesViewModel
import com.example.recipecomposeapp.features.favorites.ui.FavoritesScreen
import com.example.recipecomposeapp.features.recipes.presentation.RecipesViewModel
import com.example.recipecomposeapp.features.recipes.ui.RecipesScreen
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

@Composable
fun RecipesApp(externalIntent: Intent? = null) {

    val navController = rememberNavController()

    val jsonConfig = remember {
        Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
    }

    val loggingInterceptor = remember {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    val okHttpClient = remember {
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    val retrofit = remember {
        Retrofit.Builder()
            .baseUrl(NetworkConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(jsonConfig.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    val apiService = remember {
        retrofit.create(RecipesApiService::class.java)
    }

    val repository = remember { RecipesRepositoryImpl(apiService) }

    LaunchedEffect(externalIntent) {
        externalIntent?.let {
            delay(100)
            navController.handleDeepLink(it)
        }
    }

    val context = LocalContext.current
    val favoriteManager = remember { FavoriteDataStoreManager(context) }
    val favoriteCount by favoriteManager.getFavoriteCountFlow().collectAsState(initial = 0)

    RecipeComposeAppTheme {
        Scaffold(
            bottomBar = {
                BottomNavigation(
                    favoriteCount = favoriteCount,
                    onCategoriesClick = {
                        navController.navigate(Destination.Categories) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onFavoriteClick = {
                        navController.navigate(Destination.Favorites) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
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
                    val viewModel = remember {
                        CategoriesViewModel(repository = repository)
                    }

                    CategoriesScreen(
                        viewModel = viewModel,
                        onCategoryClick = { categoryId, categoryTitle, categoryImageUrl ->
                            navController.navigate(
                                Destination.Recipes(
                                    categoryId, categoryTitle, categoryImageUrl
                                )
                            )
                        }
                    )
                }
                composable<Destination.Favorites> {
                    val application = context.applicationContext as? Application ?: return@composable

                    val viewModel = remember {
                        FavoritesViewModel(
                            application = application,
                            repository = repository
                        )
                    }

                    FavoritesScreen(
                        viewModel = viewModel,
                        onRecipeClick = { recipeId ->
                            navController.navigate(Destination.RecipeDetails(recipeId))
                        }
                    )
                }
                composable<Destination.Recipes> { backStackEntry ->
                    val args = backStackEntry.toRoute<Destination.Recipes>()

                    val viewModel = remember(backStackEntry) {
                        RecipesViewModel(
                            model = args,
                            repository = repository
                        )
                    }

                    RecipesScreen(
                        viewModel = viewModel,
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
                    val application = context.applicationContext as? Application ?: return@composable

                    val args = backStackEntry.toRoute<Destination.RecipeDetails>()

                    val viewModel = remember(backStackEntry) {
                        RecipeDetailsViewModel(
                            application = application,
                            model = args,
                            repository = repository
                        )
                    }
                    RecipeDetailsScreen(
                        viewModel = viewModel,
                        onBackClick = { navController.navigateUp() }
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