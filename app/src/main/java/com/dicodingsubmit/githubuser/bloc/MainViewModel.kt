package com.dicodingsubmit.githubuser.bloc

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicodingsubmit.githubuser.data.remote.response.UserItemResponse
import com.dicodingsubmit.githubuser.data.remote.response.UserResponse
import com.dicodingsubmit.githubuser.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

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

	companion object {
		private const val TAG = "MainViewModel"
	}

}