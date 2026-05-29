package com.example.recipecomposeapp.data.model

import com.example.recipecomposeapp.features.categories.presentation.model.toUiModel
import com.example.recipecomposeapp.fixtures.CategoryTestFixtures
import org.junit.Assert.assertEquals
import org.junit.Test

class CategoryDtoTest {

    @Test
    fun `converts DTO to UI model`() {
        val dto = CategoryTestFixtures.createCategoryDto()
        val result = dto.toUiModel()

        assertEquals("Завтраки", result.title)
        assertEquals(1, result.id)
        assertEquals("https://recipes.androidsprint.ru/api/images/breakfast.jpg", result.imageUrl)
    }

    @Test
    fun `mapper maps empty title correctly`() {
        val dto = CategoryTestFixtures.createCategoryDto(title = "")
        val result = dto.toUiModel()

        assertEquals("", result.title)
    }

    @Test
    fun `mapper preserves very long description`() {
        val longDesc = "A".repeat(1000)
        val dto = CategoryTestFixtures.createCategoryDto(description = longDesc)
        val result = dto.toUiModel()

        assertEquals(longDesc, result.description)
    }
}