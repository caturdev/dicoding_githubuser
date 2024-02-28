package com.dicodingsubmit.githubuser.ui.activity

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.dicodingsubmit.githubuser.R

class UserDetailActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_user_detail)
	}

	companion object {
		@StringRes
		private val TAB_TITLES = intArrayOf()

		const val GITHUB_USER = "github_user"
	}
}