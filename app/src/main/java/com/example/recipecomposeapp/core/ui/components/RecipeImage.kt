package com.example.recipecomposeapp.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.recipecomposeapp.R

@Composable
fun RecipeImage(
    imageUrl: Any,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    val context = LocalContext.current

    var isLoading by remember { mutableStateOf(true) }

    val imageRequest = remember(imageUrl, context) {
        ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(300)
            .build()
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        val stubResId = R.drawable.img_error

        AsyncImage(
            model = imageRequest,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = contentScale,
            placeholder = painterResource(R.drawable.img_placeholder),
            error = painterResource(stubResId),
            fallback = painterResource(stubResId),
            onLoading = { isLoading = true },
            onSuccess = { isLoading = false },
            onError = { isLoading = false }
        )

        if (isLoading) {
            CircularProgressIndicator()
        }
    }
}