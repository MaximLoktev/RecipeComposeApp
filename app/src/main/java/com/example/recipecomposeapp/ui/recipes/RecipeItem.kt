package com.example.recipecomposeapp.ui.recipes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.ui.recipes.model.RecipeUiModel
import com.example.recipecomposeapp.ui.theme.Dimens

@Composable
fun RecipeItem(
    recipe: RecipeUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    val imageRequest = remember(recipe.imageUrl, context) {
        ImageRequest.Builder(context)
            .data(recipe.imageUrl)
            .crossfade(true)
            .build()
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(Dimens.buttonCornerRadius),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimens.mediumElevation
        )
    ) {
        Column {
            AsyncImage(
                model = imageRequest,
                contentDescription = recipe.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.iconExtraLarge),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.img_placeholder),
                error = painterResource(id = R.drawable.img_error)
            )

            Text(
                text = recipe.title.uppercase(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(Dimens.paddingSmall)
            )
        }
    }
}