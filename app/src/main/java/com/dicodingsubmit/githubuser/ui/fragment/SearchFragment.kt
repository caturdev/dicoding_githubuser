package com.dicodingsubmit.githubuser.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicodingsubmit.githubuser.R
import com.dicodingsubmit.githubuser.bloc.MainViewModel
import com.dicodingsubmit.githubuser.data.local.entity.HistoryEntity
import com.dicodingsubmit.githubuser.databinding.FragmentSearchBinding
import com.dicodingsubmit.githubuser.ui.activity.MainActivity
import com.dicodingsubmit.githubuser.ui.adapter.HistoryAdapter
import com.dicodingsubmit.githubuser.ui.adapter.utils.HistoryClickListener


class SearchFragment : Fragment(), HistoryClickListener {

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
			val usernameSearch = binding.searchBar.text.toString().trim()

			// save keyword at data store: for init keyword search when start app
			mainViewModel.saveKeyword(usernameSearch)

			// save search history
			mainViewModel.saveHistory(HistoryEntity(0, usernameSearch))

			val moveIntent = Intent(requireContext(), MainActivity::class.java)
			startActivity(moveIntent)
			requireActivity().finish()

		}

		val layoutManager = LinearLayoutManager(activity)
		binding.rvHistory.layoutManager = layoutManager

		val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
		binding.rvHistory.addItemDecoration(itemDecoration)

		// init theme mode
		mainViewModel.getAllHistory().observe(viewLifecycleOwner) { historyData ->
			val historyList: List<String> = historyData.map { data -> data.keyword }
			setHistoryListData(historyList)
		}

	}

	private fun setHistoryListData(history: List<String>) {
		val adapter = HistoryAdapter()

		adapter.submitList(history)

		// set click listener
		adapter.listener = this

		binding.rvHistory.adapter = adapter
	}

	override fun onItemClicked(view: View, keyword: String) {
		// save keyword at data store: for init keyword search when start app
		mainViewModel.saveKeyword(keyword)

		val moveIntent = Intent(requireContext(), MainActivity::class.java)
		startActivity(moveIntent)
		requireActivity().finish()
	}

}