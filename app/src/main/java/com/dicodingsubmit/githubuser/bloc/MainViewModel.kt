package com.dicodingsubmit.githubuser.bloc

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicodingsubmit.githubuser.data.local.HistoryRepo
import com.dicodingsubmit.githubuser.data.local.entity.HistoryEntity
import com.dicodingsubmit.githubuser.data.remote.response.UserItemResponse
import com.dicodingsubmit.githubuser.data.remote.response.UserResponse
import com.dicodingsubmit.githubuser.data.remote.retrofit.ApiConfig
import com.dicodingsubmit.githubuser.data.store.SettingPreferences
import com.dicodingsubmit.githubuser.data.store.dataStore
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(
	private val application: Application,
	private val pref: SettingPreferences
) : ViewModel() {

	private val mHistoryRepo: HistoryRepo = HistoryRepo(application)

	fun getThemeSettings(): LiveData<Boolean> {
		return pref.getThemeSetting().asLiveData()
	}

	fun saveThemeSetting(isDarkModeActive: Boolean) {
		viewModelScope.launch {
			pref.saveThemeSetting(isDarkModeActive)
		}
	}

	fun getLastSearchKeyword(): LiveData<String> {
		return pref.getLastSearchKeyword().asLiveData()
	}

	fun saveKeyword(keyword: String) {
		viewModelScope.launch {
			pref.saveKeyword(keyword)
		}
	}

	fun getAllHistory(data: HistoryEntity) = mHistoryRepo.insert(data)

	fun saveHistory(data: HistoryEntity) = mHistoryRepo.insert(data)

	private val _users = MutableLiveData<List<UserItemResponse>>()
	val users: LiveData<List<UserItemResponse>> = _users

	private val _isLoading = MutableLiveData<Boolean>()
	val isLoading: LiveData<Boolean> = _isLoading

	init {
		_isLoading.value = false
	}

	fun getUsers(username: String) {
		// menampilkan loading indicator
		_isLoading.value = true

		// mengosongkan list user supaya tidak bertumpuk
		_users.value = listOf()

		val client = ApiConfig.getApiService().getUser(username)
		client.enqueue(object : Callback<UserResponse> {

			// response handler untuk response
			override fun onResponse(
				call: Call<UserResponse>,
				response: Response<UserResponse>
			) {

				// menyembunyikan loading indicator
				_isLoading.value = false
				Log.e(TAG, "Res: $response")

				if (response.isSuccessful) {
					val responseBody = response.body()
					if (responseBody != null) {
						_users.value = responseBody.items
					}
				} else {
					Log.e(TAG, "Error: ${response.message()}")
				}

			}

			// response handler saat terjadi gagal koneksi
			override fun onFailure(call: Call<UserResponse>, t: Throwable) {

				// menyembunyikan loading indicator
				_isLoading.value = false

			}

		})
	}

	class Factory(
		private val application: Application,
	) : ViewModelProvider.Factory {
		private val pref: SettingPreferences = SettingPreferences.getInstance(application.dataStore)

		@Suppress("UNCHECKED_CAST")
		override fun <T : ViewModel> create(modelClass: Class<T>): T =
			MainViewModel(application, pref) as T

	}

	companion object {
		private const val TAG = "MainViewModel"
	}

}