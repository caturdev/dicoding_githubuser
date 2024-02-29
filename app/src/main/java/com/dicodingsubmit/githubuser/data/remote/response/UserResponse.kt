package com.dicodingsubmit.githubuser.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
  @field:SerializedName("total_count")
  val totalCount: Int? = null,

  @field:SerializedName("incomplete_results")
  val incompleteResults: Boolean? = null,

  @field:SerializedName("items")
  val items: List<UserItemResponse>
)

data class UserItemResponse(

  @field:SerializedName("id")
  val id: String? = null,

  @field:SerializedName("login")
  val login: String? = null,

  @field:SerializedName("avatar_url")
  val avatarUrl: String? = null
	
)



