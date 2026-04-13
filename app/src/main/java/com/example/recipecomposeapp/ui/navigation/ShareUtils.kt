package com.example.recipecomposeapp.ui.navigation

import android.content.Context
import android.content.Intent
import com.example.recipecomposeapp.Constants.DEEP_LINK_BASE_URL

object ShareUtils {

    fun createRecipeDeepLink(recipeId: Int): String {
        return "${DEEP_LINK_BASE_URL}/recipe/$recipeId"
    }

    fun shareRecipe(context: Context, recipeId: Int, recipeTitle: String) {
        val shareLink = createRecipeDeepLink(recipeId)
        val shareText = "Попробуй этот рецепт: $recipeTitle\n$shareLink"

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }

        context.startActivity(Intent.createChooser(intent, "Поделиться рецептом"))
    }
}