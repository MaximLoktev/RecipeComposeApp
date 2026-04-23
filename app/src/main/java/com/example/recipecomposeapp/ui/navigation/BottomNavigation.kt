package com.example.recipecomposeapp.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.ui.theme.Dimens

@Composable
fun BottomNavigation(
    favoriteCount: Int = 0,
    onCategoriesClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(
                horizontal =Dimens.paddingSmall,
                vertical = Dimens.paddingSmall
            ),
        horizontalArrangement = Arrangement.spacedBy(Dimens.paddingMedium)
    ) {
        Button(
            onClick = onCategoriesClick,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(Dimens.buttonCornerRadius),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary
            ),
        ) {
            Text(
                text = stringResource(R.string.categories).uppercase(),
                style = MaterialTheme.typography.titleMedium,
            )
        }

        Button(
            onClick = onFavoriteClick,
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(Dimens.buttonCornerRadius),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            ),
        ) {
            val stringFavorites = stringResource(R.string.favorites)

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringFavorites.uppercase(),
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.width(6.dp))

                BadgedBox(
                    badge = {
                        if (favoriteCount > 0) {
                            val badgeText = if (favoriteCount > 99) "99+" else favoriteCount.toString()

                            Badge(
                                containerColor = MaterialTheme.colorScheme.onError,
                                contentColor = MaterialTheme.colorScheme.error
                            ) {
                                Text(
                                    text = badgeText,
                                    style = MaterialTheme.typography.labelSmall,
                                    maxLines = 1,
                                    softWrap = false
                                )
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_heart_empty),
                        contentDescription = stringFavorites,
                        modifier = Modifier.size(Dimens.iconMedium)
                    )
                }
            }
        }
    }
}