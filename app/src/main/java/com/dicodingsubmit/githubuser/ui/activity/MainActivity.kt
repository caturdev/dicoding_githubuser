package com.dicodingsubmit.githubuser.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.dicodingsubmit.githubuser.R
import com.dicodingsubmit.githubuser.databinding.ActivityMainBinding
import com.dicodingsubmit.githubuser.ui.fragment.HomeFragment

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val fragmentManager = supportFragmentManager
    val homeFragment = HomeFragment()
    val fragment = fragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName)

    if (fragment !is HomeFragment) {
      fragmentManager.commit {
        add(R.id.frame_container, homeFragment, HomeFragment::class.java.simpleName)
      }
    }
  }
}