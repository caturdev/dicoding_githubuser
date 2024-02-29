package com.dicodingsubmit.githubuser.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicodingsubmit.githubuser.ui.fragment.UserConnectionFragment

class UserConnectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

	var username: String = ""

	override fun getItemCount(): Int {
		return 2
	}

	override fun createFragment(position: Int): Fragment {
		val fragment = UserConnectionFragment()

		fragment.arguments = Bundle().apply {
			putInt(UserConnectionFragment.ARG_SECTION_NUMBER, position + 1)
			putString(UserConnectionFragment.GITHUB_USERNAME, username)
		}

		return fragment
	}
}