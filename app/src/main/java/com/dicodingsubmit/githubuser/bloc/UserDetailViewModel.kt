package com.dicodingsubmit.githubuser.bloc

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicodingsubmit.githubuser.data.local.FavRepo
import com.dicodingsubmit.githubuser.data.local.entity.FavEntity
import com.dicodingsubmit.githubuser.data.remote.response.UserDetailResponse
import com.dicodingsubmit.githubuser.data.remote.response.UserItemResponse
import com.dicodingsubmit.githubuser.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel(application: Application) : ViewModel() {

	private val mFavRepo: FavRepo = FavRepo(application)

	private val _user = MutableLiveData<UserDetailResponse>()
	val user: LiveData<UserDetailResponse> = _user

	private val _followers = MutableLiveData<List<UserItemResponse>>()
	val followers: LiveData<List<UserItemResponse>> = _followers

	private val _following = MutableLiveData<List<UserItemResponse>>()
	val following: LiveData<List<UserItemResponse>> = _following

	private val _isLoading = MutableLiveData<Boolean>()
	val isLoading: LiveData<Boolean> = _isLoading

	fun insert(note: FavEntity) {
		mFavRepo.insert(note)
	}

	fun getUserDetail(username: String) {
		// menampilkan loading indicator
		_isLoading.value = true

		val client = ApiConfig.getApiService().getUserDetail(username)
		client.enqueue(object : Callback<UserDetailResponse> {

			override fun onResponse(
				call: Call<UserDetailResponse>,
				response: Response<UserDetailResponse>
			) {
				// menyembunyikan loading indicator
				_isLoading.value = false

				if (response.isSuccessful) {
					val responseBody = response.body()
					if (responseBody != null) {
						_user.value = responseBody ?: null
					}
				} else {
					Log.e(TAG, "Error: ${response.message()}")
				}
			}

			override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
				// menyembunyikan loading indicator
				_isLoading.value = false
			}


		})
	}

	fun getUserFollowers(username: String) {
		// menampilkan loading indicator
		_isLoading.value = true

		val client = ApiConfig.getApiService().getUserFollowers(username)
		client.enqueue(object : Callback<List<UserItemResponse>> {

			override fun onResponse(
				call: Call<List<UserItemResponse>>,
				response: Response<List<UserItemResponse>>
			) {
				// menyembunyikan loading indicator
				_isLoading.value = false

				if (response.isSuccessful) {
					val responseBody = response.body()
					if (responseBody != null) {
						_followers.value = responseBody ?: null
					}
				} else {
					Log.e(TAG, "Error: ${response.message()}")
				}
			}

			override fun onFailure(call: Call<List<UserItemResponse>>, t: Throwable) {
				// menyembunyikan loading indicator
				_isLoading.value = false
			}

		})

	}

	fun getUserFollowing(username: String) {
		// menampilkan loading indicator
		_isLoading.value = true

		val client = ApiConfig.getApiService().getUserFollowing(username)
		client.enqueue(object : Callback<List<UserItemResponse>> {

			override fun onResponse(
				call: Call<List<UserItemResponse>>,
				response: Response<List<UserItemResponse>>
			) {
				// menyembunyikan loading indicator
				_isLoading.value = false

				if (response.isSuccessful) {
					val responseBody = response.body()
					if (responseBody != null) {
						_following.value = responseBody ?: null
					}
				} else {
					Log.e(TAG, "Error: ${response.message()}")
				}
			}

			override fun onFailure(call: Call<List<UserItemResponse>>, t: Throwable) {
				// menyembunyikan loading indicator
				_isLoading.value = false
			}

		})

	}

	companion object {
		private const val TAG = "ProfileViewModel"
	}
}