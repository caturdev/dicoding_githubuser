package com.dicodingsubmit.githubuser.bloc.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicodingsubmit.githubuser.bloc.MainViewModel
import com.dicodingsubmit.githubuser.bloc.UserDetailViewModel
import com.dicodingsubmit.githubuser.data.store.SettingPreferences

class ViewModelFactory private constructor(
	private val application: Application,
	private val pref: SettingPreferences
) : ViewModelProvider.NewInstanceFactory() {

	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel> create(modelClass: Class<T>): T {

		if (modelClass.isAssignableFrom(UserDetailViewModel::class.java)) {
			return UserDetailViewModel(application) as T
		}

		if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
			return MainViewModel(pref) as T
		}

		throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)

	}

	companion object {

		@Volatile
		private var instance: ViewModelFactory? = null

		@JvmStatic
		fun getInstance(
			application: Application,
			pref: SettingPreferences
		): ViewModelFactory = instance ?: synchronized(this) {
			instance ?: ViewModelFactory(
				application,
				pref
			)
		}.also { instance = it }

	}

}