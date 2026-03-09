package com.example.recipecomposeapp.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

val recipesAppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = montserratAlternatesFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    ),
    titleMedium = TextStyle(
        fontFamily = montserratAlternatesFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = montserratFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = montserratFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    ),
    labelLarge = TextStyle(
        fontFamily = montserratFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)

@Preview(showBackground = true)
@Composable
fun TypographyPreview() {
    RecipeComposeAppTheme {
        Scaffold { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                Text(
                    text = "displayLarge - Заголовки экранов",
                    style = MaterialTheme.typography.displayLarge
                )
                Text(
                    text = "titleMedium - Карточки",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "bodyMedium - Основной текст",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "bodySmall - Мелкий текст",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "labelLarge - Кнопки",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}