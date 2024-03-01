package com.dicodingsubmit.githubuser.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "history")
@Parcelize
class HistoryEntity(

	@field:ColumnInfo(name = "id")
	@field:PrimaryKey(autoGenerate = true)
	val id: Int,

	@field:ColumnInfo(name = "keyword")
	val keyword: String

) : Parcelable