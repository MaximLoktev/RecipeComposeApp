package com.example.recipecomposeapp.features.screens

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.KNode

class CategoriesComposeScreen(semanticsProvider: SemanticsNodeInteractionsProvider) :
    ComposeScreen<CategoriesComposeScreen>(
        semanticsProvider = semanticsProvider,
        viewBuilderAction = { hasTestTag("categories_screen") }
    ) {
    val categoriesGrid: KNode = child { hasTestTag("categories_grid") }
    val categoryItem: KNode = child { hasTestTag("category_item") }
}