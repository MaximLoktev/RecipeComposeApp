package com.example.recipecomposeapp.features.categories.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import com.example.recipecomposeapp.core.ui.components.RecipeImage
import com.example.recipecomposeapp.core.ui.theme.Dimens
import com.example.recipecomposeapp.features.categories.presentation.model.CategoryUiModel

@Composable
fun CategoryItem(
    category: CategoryUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
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
        Column(
            verticalArrangement = Arrangement.spacedBy(Dimens.paddingSmall)
        ) {
            RecipeImage(
                imageUrl = category.imageUrl,
                contentDescription = category.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.2f),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(
                    start = Dimens.paddingSmall,
                    end = Dimens.paddingSmall,
                    bottom = Dimens.paddingSmall
                ),
                verticalArrangement = Arrangement.spacedBy(Dimens.paddingSmall)
            ) {
                Text(
                    text = category.title.uppercase(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = category.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}