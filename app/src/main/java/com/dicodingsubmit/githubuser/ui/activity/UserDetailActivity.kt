package com.dicodingsubmit.githubuser.ui.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicodingsubmit.githubuser.bloc.UserDetailViewModel
import com.dicodingsubmit.githubuser.data.parcel.User
import com.dicodingsubmit.githubuser.data.remote.response.UserDetailResponse
import com.dicodingsubmit.githubuser.databinding.ActivityUserDetailBinding

class UserDetailActivity : AppCompatActivity() {

	private lateinit var binding: ActivityUserDetailBinding
	private lateinit var toolbar: Toolbar

	private val userDetailViewModel: UserDetailViewModel by viewModels<UserDetailViewModel>()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityUserDetailBinding.inflate(layoutInflater)
		setContentView(binding.root)

		toolbar = binding.toolbar
		setSupportActionBar(toolbar)

		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		supportActionBar?.setDisplayShowHomeEnabled(true)

		val githubUser = if (Build.VERSION.SDK_INT >= 33) {
			intent.getParcelableExtra(GITHUB_USER, User::class.java)
		} else {
			@Suppress("DEPRECATION")
			intent.getParcelableExtra(GITHUB_USER)
		}

		userDetailViewModel.getUserDetail(githubUser?.username ?: "")

		userDetailViewModel.user.observe(this) { user ->
			setUserData(user)

			val viewPager: ViewPager2 = binding.viewPager

//			val sectionPagerAdapter = FollowPagerAdapter(this)
//			sectionPagerAdapter.username = githubUser?.username ?: ""
//
//			viewPager.adapter = sectionPagerAdapter
//			val tabs: TabLayout = binding.tabs
//
//			TabLayoutMediator(tabs, viewPager) { tab, position ->
//				tab.text = when (position) {
//					0 -> "Followers ${user.followers.toString()}"
//					1 -> "Following ${user.following.toString()}"
//					else -> ""
//				}
//			}.attach()
		}

	}

	private fun setUserData(user: UserDetailResponse): Unit {

		// menampilkan user avatar
		Glide
			.with(binding.photoProfile.context)
			.load(user.avatarUrl)
			.into(binding.photoProfile)

		binding.photoProfile.visibility = View.VISIBLE

		// show name data
		binding.name.text = user.name

		// show username data
		"@${user.login}".also { binding.username.text = it }

		// show location data
		binding.location.text = user.location

		// show company data
		binding.company.text = user.company

	}

	companion object {
		@StringRes
		private val TAB_TITLES = intArrayOf()

		const val GITHUB_USER = "github_user"
	}
}