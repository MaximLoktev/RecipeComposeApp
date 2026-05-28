package com.example.recipecomposeapp.data.model

import com.example.recipecomposeapp.core.Constants
import com.example.recipecomposeapp.features.recipes.presentation.model.toUiModel
import com.example.recipecomposeapp.fixtures.RecipeTestFixtures
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class RecipeDtoMapperTest {

    @Test
    fun `maps DTO to UI model correctly`() {
        val dto = RecipeTestFixtures.createRecipeDto()
        val result = dto.toUiModel()

        assertEquals(1, result.id)
        assertEquals("Pasta Carbonara", result.title)
        assertTrue(result.ingredients.isNotEmpty())
    }

    @Test
    fun `prepends base url to relative imageUrl`() {
        val dto = RecipeTestFixtures.createRecipeDto(imageUrl = "pasta.jpg")
        val result = dto.toUiModel()

        assertEquals(Constants.IMAGES_BASE_URL + "pasta.jpg", result.imageUrl)
    }

    @Test
    fun `preserves full imageUrl starting with http`() {
        val fullUrl = "https://example.com/image.jpg"
        val dto = RecipeTestFixtures.createRecipeDto(imageUrl = fullUrl)
        val result = dto.toUiModel()

        assertEquals(fullUrl, result.imageUrl)
    }
}