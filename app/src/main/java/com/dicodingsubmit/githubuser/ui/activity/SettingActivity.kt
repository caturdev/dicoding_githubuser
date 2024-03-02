package com.dicodingsubmit.githubuser.ui.activity

import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.dicodingsubmit.githubuser.R
import com.dicodingsubmit.githubuser.bloc.MainViewModel
import com.dicodingsubmit.githubuser.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

	private lateinit var binding: ActivitySettingBinding

	private val mainViewModel: MainViewModel by viewModels<MainViewModel> {
		MainViewModel.Factory(application)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivitySettingBinding.inflate(layoutInflater)
		setContentView(binding.root)

		setSupportActionBar(binding.toolbar)

		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		supportActionBar?.setDisplayShowHomeEnabled(true)

		binding.toolbar.setNavigationOnClickListener {
			finish()
		}

		// init theme mode
		mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
			if (isDarkModeActive) {
				setNightMode()
			} else {
				setLightMode()
			}
		}

		binding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
			if (isChecked) {
				mainViewModel.saveThemeSetting(true)
				setNightMode()
			} else {
				mainViewModel.saveThemeSetting(false)
				setLightMode()
			}
		}

	}

	private fun setLightMode() {
		AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
		binding.settingModeIcon.setImageResource(R.drawable.baseline_light_mode_24)
		binding.settingTitle.setText(R.string.switch_dark_mode)
		binding.switchTheme.isChecked = false
	}

	private fun setNightMode() {
		AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
		binding.settingModeIcon.setImageResource(R.drawable.baseline_dark_mode_24)
		binding.settingTitle.setText(R.string.switch_light_mode)
		binding.switchTheme.isChecked = true
	}
}