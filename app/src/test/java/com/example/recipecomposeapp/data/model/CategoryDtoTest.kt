package com.example.recipecomposeapp.data.model

import com.example.recipecomposeapp.features.categories.presentation.model.toUiModel
import org.junit.Assert.assertEquals
import org.junit.Test

class CategoryDtoTest {

    @Test
    fun `converts DTO to UI model`() {
        val dto = CategoryDto(
            id = 1,
            title = "Завтраки",
            description = "Утренние блюда",
            imageUrl = "breakfast.jpg"
        )

        val result = dto.toUiModel()

        assertEquals("Завтраки", result.title)
        assertEquals(1, result.id)
        assertEquals("https://recipes.androidsprint.ru/api/images/breakfast.jpg", result.imageUrl)
    }
}