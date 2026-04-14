package com.example.recipecomposeapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import com.example.recipecomposeapp.ui.theme.Dimens

@Composable
fun ScreenHeader(
    painter: Painter,
    contentDescription: String,
    text: String,
    onBackClick: (() -> Unit)? = null,
    showShareButton: Boolean = false,
    onShareClick: () -> Unit = {}
) {

    Box(
        Modifier
            .height(Dimens.headerHeight)
            .fillMaxWidth()
    ) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        if (onBackClick != null) {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = Dimens.paddingLarge, start = Dimens.paddingLarge)
                    .size(Dimens.iconButtonSize)
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Назад",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        if (showShareButton) {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                modifier = Modifier
                    .align(Alignment.TopEnd) // Выравниваем по правому краю
                    .padding(top = Dimens.paddingLarge, end = Dimens.paddingLarge) // Отступ справа
                    .size(Dimens.iconButtonSize)
            ) {
                IconButton(onClick = onShareClick) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Поделиться",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        Surface(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(Dimens.paddingLarge),
            shape = RoundedCornerShape(Dimens.buttonCornerRadius),
            color = MaterialTheme.colorScheme.surface
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(Dimens.paddingMedium)
            )
        }
    }
}