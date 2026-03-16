package com.example.recipecomposeapp.ui.categories

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.ui.components.ScreenHeader
import com.example.recipecomposeapp.ui.theme.Dimens

@Composable
fun CategoriesScreen(modifier: Modifier = Modifier) {

    val categories = stringResource(R.string.categories)

    Column(modifier = modifier.fillMaxSize()) {
        ScreenHeader(
            painterResource(id = R.drawable.bcg_categories),
            contentDescription = categories,
            text = categories.uppercase()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.paddingLarge),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Здесь будет список категорий",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}