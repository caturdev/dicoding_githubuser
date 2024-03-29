package com.dicodingsubmit.githubuser.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicodingsubmit.githubuser.bloc.UserDetailViewModel
import com.dicodingsubmit.githubuser.data.remote.response.UserItemResponse
import com.dicodingsubmit.githubuser.databinding.FragmentUserConnectionBinding
import com.dicodingsubmit.githubuser.ui.adapter.UserAdapter

class UserConnectionFragment : Fragment() {

	private lateinit var binding: FragmentUserConnectionBinding

	private val userDetailViewModel: UserDetailViewModel by viewModels<UserDetailViewModel> {
		UserDetailViewModel.Factory((activity as AppCompatActivity).application)
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentUserConnectionBinding.inflate(layoutInflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val username = arguments?.getString(GITHUB_USERNAME)
		val position = arguments?.getInt(ARG_SECTION_NUMBER)

		val layoutManager = LinearLayoutManager(activity)
		binding.rvUserConnection.layoutManager = layoutManager

		val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
		binding.rvUserConnection.addItemDecoration(itemDecoration)

		// menentukan tindakan apabila section sekarang adalah followers (index 1)
		if (position == 1) {
			userDetailViewModel.getUserFollowers(username ?: "")
		}

		// menentukan tindakan apabila section sekarang adalah following (index 2)
		if (position == 2) {
			userDetailViewModel.getUserFollowing(username ?: "")
		}

		userDetailViewModel.followers.observe(viewLifecycleOwner) { users: List<UserItemResponse> ->
			setUserFollow(users)
			isLoading(false)
		}
		userDetailViewModel.following.observe(viewLifecycleOwner) { users: List<UserItemResponse> ->
			setUserFollow(users)
			isLoading(false)
		}

	}

	private fun isLoading(condition: Boolean = true): Unit = if (condition) {
		binding.loadingIndicator.visibility = View.VISIBLE
	} else {
		binding.loadingIndicator.visibility = View.GONE
	}

	private fun setUserFollow(users: List<UserItemResponse>): Unit {
		val adapter = UserAdapter()
		adapter.submitList(users)
		binding.rvUserConnection.adapter = adapter
	}

	companion object {
		const val ARG_SECTION_NUMBER = "section_number"
		const val GITHUB_USERNAME = "github_username"
	}

}