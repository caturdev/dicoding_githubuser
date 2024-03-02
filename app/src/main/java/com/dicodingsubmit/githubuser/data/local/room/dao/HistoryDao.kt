package com.dicodingsubmit.githubuser.data.local.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicodingsubmit.githubuser.data.local.entity.HistoryEntity

@Dao
interface HistoryDao {

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	fun insert(favEntity: HistoryEntity)

	@Delete
	fun delete(favEntity: HistoryEntity)

	@Query("SELECT * from history ORDER BY id DESC")
	fun getAll(): LiveData<List<HistoryEntity>>

}