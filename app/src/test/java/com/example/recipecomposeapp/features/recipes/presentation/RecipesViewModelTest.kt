package com.example.recipecomposeapp.features.recipes.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import app.cash.turbine.test
import com.example.recipecomposeapp.core.ui.navigation.Destination
import com.example.recipecomposeapp.data.repository.RecipesRepository
import com.example.recipecomposeapp.fixtures.RecipeTestFixtures
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException
import kotlin.collections.emptyList

@OptIn(ExperimentalCoroutinesApi::class)
class RecipesViewModelTest {

    private val repository = mockk<RecipesRepository>()
    private lateinit var viewModel: RecipesViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `loads recipes for category`() = runTest {
        val categoryId = 1
        val recipes = RecipeTestFixtures.createRecipeDtoList()
        every { repository.getRecipesByCategory(categoryId) } returns flowOf(recipes)

        viewModel = createViewModel(categoryId = categoryId)

        viewModel.uiState.test {
            val state = awaitItem()

            assertEquals(3, state.recipes.size)
            assertFalse(state.isLoading)
            assertFalse(state.isError)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `state reflects category title from savedState`() = runTest {
        val testTitle = "Обеды"
        every { repository.getRecipesByCategory(any()) } returns flowOf(emptyList())

        viewModel = createViewModel(categoryTitle = testTitle)

        viewModel.uiState.test {
            val state = awaitItem()

            assertEquals(testTitle, state.categoryTitle)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `shows error when repository throws`() = runTest {
        every { repository.getRecipesByCategory(any()) } returns flow {
            throw IOException("DB Error")
        }

        viewModel = createViewModel()

        viewModel.uiState.test {
            val state = awaitItem()

            assertTrue(state.isError)
            assertFalse(state.isLoading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun createViewModel(
        categoryId: Int = 1,
        categoryTitle: String = "Завтраки",
        categoryImageUrl: String = "url"
    ): RecipesViewModel {
        val savedStateHandle = mockk<SavedStateHandle>(relaxed = true)

        mockkStatic("androidx.navigation.SavedStateHandleKt")

        val fakeRoute = Destination.Recipes(
            categoryId, categoryTitle, categoryImageUrl
        )

        every { savedStateHandle.toRoute<Destination.Recipes>() } returns fakeRoute

        return RecipesViewModel(savedStateHandle, repository)
    }
}