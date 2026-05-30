package com.example.recipecomposeapp.features.categories.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.recipecomposeapp.features.categories.presentation.model.CategoriesUiState
import com.example.recipecomposeapp.features.categories.presentation.model.CategoryUiModel
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class CategoriesContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysCategories() {
        composeTestRule.setContent {
            CategoriesContent(
                uiState = CategoriesUiState(
                    categories = listOf(CategoryUiModel(
                        id = 1,
                        title = "Завтраки",
                        description = "",
                        imageUrl = "")
                    )
                ),
                onCategoryClick = { _, _, _ -> },
                onRetryClick = {}
            )
        }
        composeTestRule.onNodeWithText("ЗАВТРАКИ").assertIsDisplayed()
    }

    @Test
    fun clickingCategoryNavigatesToRecipes() {
        var clickedId: Int? = null

        composeTestRule.setContent {
            CategoriesContent(
                uiState = CategoriesUiState(
                    categories = listOf(CategoryUiModel(
                        id = 1,
                        title = "Завтраки",
                        description = "",
                        imageUrl = "")
                    )
                ),
                onCategoryClick = { id, _, _ -> clickedId = id },
                onRetryClick = {}
            )
        }
        composeTestRule.onNodeWithText("ЗАВТРАКИ").performClick()
        assertEquals(1, clickedId)
    }

    @Test
    fun showsLoadingState() {
        composeTestRule.setContent {
            CategoriesContent(
                uiState = CategoriesUiState(isLoading = true, categories = emptyList()),
                onCategoryClick = { _, _, _ -> },
                onRetryClick = {}
            )
        }
        composeTestRule.onNodeWithTag("loading_indicator").assertIsDisplayed()
    }
}