package com.example.recipecomposeapp.features.recipes.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipesUiState
import org.junit.Rule
import org.junit.Test

class RecipesContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun showsLoadingState() {
        composeTestRule.setContent {
            RecipesContent(
                uiState = RecipesUiState(isLoading = true),
                onRecipeClick = { _, _ -> },
                onBackClick = {},
                onRetryClick = {}
            )
        }
        composeTestRule.onNodeWithTag("loading_indicator").assertIsDisplayed()
    }

    @Test
    fun showsErrorState() {
        composeTestRule.setContent {
            RecipesContent(
                uiState = RecipesUiState(isError = true),
                onRecipeClick = { _, _ -> },
                onBackClick = {},
                onRetryClick = {}
            )
        }
        composeTestRule.onNodeWithTag("error_message").assertIsDisplayed()
    }

    @Test
    fun showsEmptyState() {
        composeTestRule.setContent {
            RecipesContent(
                uiState = RecipesUiState(),
                onRecipeClick = { _, _ -> },
                onBackClick = {},
                onRetryClick = {}
            )
        }
        composeTestRule.onNodeWithTag("empty_state").assertIsDisplayed()
    }

    @Test
    fun displaysRecipeList() {
        composeTestRule.setContent {
            RecipesContent(
                uiState = RecipesUiState(
                    recipes = listOf(RecipeUiModel(
                        id = 1,
                        title = "Суп",
                        imageUrl = "",
                        ingredients = emptyList(),
                        method = emptyList(),
                        isFavorite = false
                    ))
                ),
                onRecipeClick = { _, _ -> },
                onBackClick = {},
                onRetryClick = {}
            )
        }
        composeTestRule.onNodeWithText("СУП").assertIsDisplayed()
    }
}