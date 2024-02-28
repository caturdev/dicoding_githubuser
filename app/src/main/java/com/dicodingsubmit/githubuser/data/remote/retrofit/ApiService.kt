package com.dicodingsubmit.githubuser.data.remote.retrofit

import com.dicodingsubmit.githubuser.data.remote.response.UserDetailResponse
import com.dicodingsubmit.githubuser.data.remote.response.UserItemResponse
import com.dicodingsubmit.githubuser.data.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

	// search user data by username querystring
	@GET("search/users")
	fun getUser(@Query("q") name: String): Call<UserResponse>

	// get user detail data
	@GET("users/{username}")
	fun getUserDetail(@Path("username") username: String): Call<UserDetailResponse>

	// git followers list data
	@GET("users/{username}/followers")
	fun getUserFollowers(@Path("username") username: String): Call<List<UserItemResponse>>

	// get following list data
	@GET("users/{username}/following")
	fun getUserFollowing(@Path("username") username: String): Call<List<UserItemResponse>>

}