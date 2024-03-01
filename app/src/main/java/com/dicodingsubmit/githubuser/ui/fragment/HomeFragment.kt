package com.dicodingsubmit.githubuser.ui.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
	private val mainViewModel: MainViewModel by viewModels<MainViewModel>()

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

		mainViewModel.users.observe(viewLifecycleOwner) { user -> setUserListData(user) }

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

					val nightModeFlags =
						requireContext().resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

					if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
						it.setIcon(R.drawable.baseline_mode_night_24)
						AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
					} else {
						it.setIcon(R.drawable.baseline_light_mode_24)
						AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
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

	companion object {
		const val EXTRA_USERNAME_SEARCH = "extra_username_search"
	}
}