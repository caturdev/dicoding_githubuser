package com.dicodingsubmit.githubuser.bloc.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicodingsubmit.githubuser.bloc.UserDetailViewModel

class ViewModelFactory private constructor(
	private val application: Application
) : ViewModelProvider.NewInstanceFactory() {

	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(UserDetailViewModel::class.java)) {
			return UserDetailViewModel(application) as T
		}
		throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
	}

	companion object {

		@Volatile
		private var instance: ViewModelFactory? = null

		@JvmStatic
		fun getInstance(application: Application): ViewModelFactory =
			instance ?: synchronized(this) {
				instance ?: ViewModelFactory(application)
			}.also { instance = it }
	}

}