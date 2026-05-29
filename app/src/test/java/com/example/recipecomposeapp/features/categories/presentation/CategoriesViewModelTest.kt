package com.example.recipecomposeapp.features.categories.presentation

import app.cash.turbine.test
import com.example.recipecomposeapp.data.repository.RecipesRepository
import com.example.recipecomposeapp.fixtures.CategoryTestFixtures
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
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

@OptIn(ExperimentalCoroutinesApi::class)
class CategoriesViewModelTest {

    private val repository = mockk<RecipesRepository>()

    private lateinit var viewModel: CategoriesViewModel

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
    fun `loads categories from repository`() = runTest {
        val categories = CategoryTestFixtures.createCategoryDtoList()
        every { repository.getCategories() } returns flowOf(categories)

        viewModel = CategoriesViewModel(repository)

        viewModel.uiState.test {
            val state = awaitItem()

            assertEquals(3, state.categories.size)
            assertFalse(state.isLoading)
            assertFalse(state.isError)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `shows empty list when repository returns no data`() = runTest {
        every { repository.getCategories() } returns flowOf(emptyList())

        viewModel = CategoriesViewModel(repository)

        viewModel.uiState.test {
            val state = awaitItem()

            assertTrue(state.categories.isEmpty())
            assertFalse(state.isLoading)
            assertFalse(state.isError)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `shows error when repository throws`() = runTest {
        every { repository.getCategories() } returns flow {
            throw IOException("Network Error")
        }

        viewModel = CategoriesViewModel(repository)

        viewModel.uiState.test {
            val state = awaitItem()

            assertTrue(state.isError)
            assertFalse(state.isLoading)
            cancelAndIgnoreRemainingEvents()
        }
    }
}