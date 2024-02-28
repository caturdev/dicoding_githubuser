package com.dicodingsubmit.githubuser.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dicodingsubmit.githubuser.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

  private lateinit var binding: FragmentSearchBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // init binding and inflate the layout for this fragment
    binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

//    with(binding) {
//      searchView.setupWithSearchBar(searchBar)
//
//    }
  }

}