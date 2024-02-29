package com.dicodingsubmit.githubuser.data.local.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dicodingsubmit.githubuser.data.local.entity.FavEntity

@Dao
interface FavDao {

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	fun insert(note: FavEntity)

	@Update
	fun update(note: FavEntity)

	@Delete
	fun delete(note: FavEntity)

	@Query("SELECT * from fav ORDER BY id ASC")
	fun getAll(): LiveData<List<FavEntity>>

}