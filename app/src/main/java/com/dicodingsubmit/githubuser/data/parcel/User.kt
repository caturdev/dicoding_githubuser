package com.dicodingsubmit.githubuser.data.parcel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
	val username: String?
) : Parcelable