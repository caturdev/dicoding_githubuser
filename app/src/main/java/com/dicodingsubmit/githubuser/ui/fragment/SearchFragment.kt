package com.dicodingsubmit.githubuser.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.dicodingsubmit.githubuser.R
import com.dicodingsubmit.githubuser.bloc.MainViewModel
import com.dicodingsubmit.githubuser.data.local.entity.HistoryEntity
import com.dicodingsubmit.githubuser.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {

	private lateinit var binding: FragmentSearchBinding

	private val mainViewModel: MainViewModel by viewModels<MainViewModel> {
		MainViewModel.Factory((activity as AppCompatActivity).application)
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		// init binding and inflate the layout for this fragment
		binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		(activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
		(activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
		(activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
		(activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

		binding.toolbar.setNavigationOnClickListener {
			parentFragmentManager.commit {
				replace(
					R.id.frame_container,
					HomeFragment(),
					HomeFragment::class.java.simpleName
				)
			}
		}

		binding.searchBar.setOnClickListener {
			val homeFragment = HomeFragment()

			val usernameSearch = binding.searchBar.text.toString().trim()

			val bundle = Bundle()

			bundle.putString(HomeFragment.EXTRA_USERNAME_SEARCH, usernameSearch)

			homeFragment.arguments = bundle

			// save keyword at data store: for init keyword search when start app
			mainViewModel.saveKeyword(usernameSearch)

			// save search history
			mainViewModel.saveHistory(HistoryEntity(0, usernameSearch))

			// back to home fragment with search keyword
			parentFragmentManager.commit {
				replace(
					R.id.frame_container,
					homeFragment,
					HomeFragment::class.java.simpleName
				)
			}
		}

	}

}