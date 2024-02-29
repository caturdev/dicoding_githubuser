package com.dicodingsubmit.githubuser.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "fav")
@Parcelize
class FavEntity(
	@field:ColumnInfo(name = "id")
	@field:PrimaryKey
	val id: String,

	@field:ColumnInfo(name = "name")
	val name: String,

	@field:ColumnInfo(name = "username")
	val username: String,

	@field:ColumnInfo(name = "picture")
	val picture: String,
) : Parcelable