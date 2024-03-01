package com.dicodingsubmit.githubuser.data.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {

	private val themeKey = booleanPreferencesKey("theme_setting")
	private val lastSearchKeyword = stringPreferencesKey("last_search_keyword")

	fun getThemeSetting(): Flow<Boolean> {
		return dataStore.data.map { preferences ->
			preferences[themeKey] ?: false
		}
	}

	suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
		dataStore.edit { preferences ->
			preferences[themeKey] = isDarkModeActive
		}
	}

	fun getLastSearchKeyword(): Flow<String> {
		return dataStore.data.map { preferences ->
			preferences[lastSearchKeyword] ?: ""
		}
	}

	suspend fun saveKeyword(isDarkModeActive: String) {
		dataStore.edit { preferences ->
			preferences[lastSearchKeyword] = isDarkModeActive
		}
	}

	companion object {

		@Volatile
		private var INSTANCE: SettingPreferences? = null

		fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
			return INSTANCE ?: synchronized(this) {
				val instance = SettingPreferences(dataStore)
				INSTANCE = instance
				instance
			}
		}

	}

}