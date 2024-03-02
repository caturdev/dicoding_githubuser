package com.dicodingsubmit.githubuser.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicodingsubmit.githubuser.R
import com.dicodingsubmit.githubuser.bloc.MainViewModel
import com.dicodingsubmit.githubuser.data.remote.response.UserItemResponse
import com.dicodingsubmit.githubuser.databinding.FragmentHomeBinding
import com.dicodingsubmit.githubuser.ui.activity.SettingActivity
import com.dicodingsubmit.githubuser.ui.adapter.UserAdapter

class HomeFragment : Fragment() {

	private lateinit var binding: FragmentHomeBinding

	private val mainViewModel: MainViewModel by viewModels<MainViewModel> {
		MainViewModel.Factory((activity as AppCompatActivity).application)
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		// init binding and inflate the layout for this fragment
		binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		binding.noDataLayer.visibility = View.GONE

		val fragmentManager = parentFragmentManager
		val searchFragment = SearchFragment()

		// init theme mode
		mainViewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
			if (isDarkModeActive) {
				AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
			} else {
				AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
			}
		}

		// init theme mode
		mainViewModel.getLastSearchKeyword().observe(viewLifecycleOwner) { keyword: String ->
			if (keyword.isNotEmpty()) {
				mainViewModel.getUsers(keyword ?: "")
				binding.noDataLayer.visibility = View.GONE
			} else {
				binding.noDataLayer.visibility = View.VISIBLE
			}
			isLoading(false)
		}

		mainViewModel.users.observe(viewLifecycleOwner) { user ->
			if (user.isNotEmpty()) {
				setUserListData(user)
				binding.noDataLayer.visibility = View.GONE
			} else {
				binding.noDataLayer.visibility = View.VISIBLE
			}
		}

		val layoutManager = LinearLayoutManager(activity)
		binding.rvUser.layoutManager = layoutManager

		val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
		binding.rvUser.addItemDecoration(itemDecoration)

		binding.fab.setOnClickListener {
			fragmentManager.commit {
				replace(R.id.frame_container, searchFragment, HomeFragment::class.java.simpleName)
				addToBackStack(null)
			}
		}

		binding.fabGotoFav.setOnClickListener {
			fragmentManager.commit {
				replace(R.id.frame_container, FavFragment(), FavFragment::class.java.simpleName)
				addToBackStack(null)
			}
		}

		binding.toolbar.setOnMenuItemClickListener {
			when (it.itemId) {
				R.id.menu_mode -> {
					val moveIntent = Intent(requireContext(), SettingActivity::class.java)
					startActivity(moveIntent)
				}
			}
			true
		}
	}

	private fun setUserListData(user: List<UserItemResponse>) {
		val adapter = UserAdapter()
		adapter.submitList(user)
		binding.rvUser.adapter = adapter
	}

	private fun isLoading(condition: Boolean = true): Unit = if (condition) {
		binding.loadingIndicator.visibility = View.VISIBLE
	} else {
		binding.loadingIndicator.visibility = View.GONE
	}

}