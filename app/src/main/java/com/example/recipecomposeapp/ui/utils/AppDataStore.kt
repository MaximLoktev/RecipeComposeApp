package com.example.recipecomposeapp.ui.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.recipecomposeapp.Constants

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = Constants.PREFS_FILE_NAME,
    produceMigrations = { context ->
        listOf(
            SharedPreferencesMigration(
                context = context,
                sharedPreferencesName = Constants.PREFS_FILE_NAME
            )
        )
    }
)

object PreferencesKeys {
    val FAVORITE_RECIPE_IDS = stringSetPreferencesKey(Constants.FAVORITES_KEY_NAME)
}