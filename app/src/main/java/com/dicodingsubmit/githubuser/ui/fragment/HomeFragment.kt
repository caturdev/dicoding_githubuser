package com.dicodingsubmit.githubuser.ui.fragment

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
import com.dicodingsubmit.githubuser.ui.adapter.UserAdapter

class HomeFragment : Fragment() {

	private lateinit var binding: FragmentHomeBinding
	private var isDaskMode: Boolean = false

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

		val fragmentManager = parentFragmentManager
		val searchFragment = SearchFragment()

		// loading section
		binding.lottieLoading.setAnimationFromUrl("https://lottie.host/f4aa2a91-160f-40bf-927a-85ca4d9f1074/HesvD4FI65.json")

		// init theme mode
		mainViewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
			isDaskMode = isDarkModeActive

			if (isDarkModeActive) {
				AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
				binding.toolbar.menu.findItem(R.id.menu_mode).setIcon(R.drawable.baseline_light_mode_24)
			} else {
				AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
				binding.toolbar.menu.findItem(R.id.menu_mode).setIcon(R.drawable.baseline_dark_mode_24)
			}
		}

		// init theme mode
		mainViewModel.getLastSearchKeyword().observe(viewLifecycleOwner) { keyword: String ->
			if (keyword.isNotEmpty()) mainViewModel.getUsers(keyword ?: "")
		}

		mainViewModel.users.observe(viewLifecycleOwner) { user ->
			if (user.isNotEmpty()) {
				setUserListData(user)
				isLoading(false)
			}
		}

		if (arguments != null) {
			val usernameSearch = arguments?.getString(EXTRA_USERNAME_SEARCH)
			mainViewModel.getUsers(usernameSearch ?: "")
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

					if (!isDaskMode) {
						AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
						mainViewModel.saveThemeSetting(true)
					} else {
						AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
						mainViewModel.saveThemeSetting(false)
					}

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
		binding.lottieLoading.visibility = View.VISIBLE
	} else {
		binding.lottieLoading.visibility = View.GONE
	}

	companion object {
		const val EXTRA_USERNAME_SEARCH = "extra_username_search"
	}
}