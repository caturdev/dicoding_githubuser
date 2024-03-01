package com.dicodingsubmit.githubuser.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicodingsubmit.githubuser.R
import com.dicodingsubmit.githubuser.bloc.UserDetailViewModel
import com.dicodingsubmit.githubuser.data.remote.response.UserItemResponse
import com.dicodingsubmit.githubuser.databinding.FragmentFavBinding
import com.dicodingsubmit.githubuser.ui.adapter.UserAdapter
import com.dicodingsubmit.githubuser.utils.helper.ViewModelHelper

class FavFragment : Fragment() {

	private lateinit var binding: FragmentFavBinding
	private lateinit var userDetailViewModel: UserDetailViewModel

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentFavBinding.inflate(layoutInflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		userDetailViewModel = ViewModelHelper.obtainUserDetailViewModel(activity as AppCompatActivity)

		(activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
		(activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
		(activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

		binding.toolbar.setNavigationOnClickListener {
			parentFragmentManager.commit {
				replace(
					R.id.frame_container,
					HomeFragment(),
					HomeFragment::class.java.simpleName
				)
			}
		}

		val layoutManager = LinearLayoutManager(activity)
		binding.rvUserFav.layoutManager = layoutManager

		val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
		binding.rvUserFav.addItemDecoration(itemDecoration)

		userDetailViewModel.getAllFav().observe(viewLifecycleOwner) { favData ->
			val userItemList: List<UserItemResponse> = favData.map { data ->
				UserItemResponse(
					data.id,
					data.username,
					data.picture
				)
			}

			setUserFavListData(userItemList)
		}
	}

	private fun setUserFavListData(user: List<UserItemResponse>) {
		val adapter = UserAdapter()
		adapter.submitList(user)
		binding.rvUserFav.adapter = adapter
	}

}