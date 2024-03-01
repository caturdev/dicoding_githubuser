package com.dicodingsubmit.githubuser.utils.helper

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicodingsubmit.githubuser.bloc.UserDetailViewModel
import com.dicodingsubmit.githubuser.bloc.factory.ViewModelFactory
import com.dicodingsubmit.githubuser.data.store.SettingPreferences
import com.dicodingsubmit.githubuser.data.store.dataStore

class ViewModelHelper {

	companion object {

		fun obtainUserDetailViewModel(
			activity: AppCompatActivity,
		): UserDetailViewModel {
			val pref = SettingPreferences.getInstance(activity.application.dataStore)
			val factory = ViewModelFactory.getInstance(activity.application, pref)
			return ViewModelProvider(activity, factory)[UserDetailViewModel::class.java]
		}

	}
	
}