package com.dicodingsubmit.githubuser.data.local.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dicodingsubmit.githubuser.data.local.entity.FavEntity

@Dao
interface FavDao {
	@Query("SELECT * FROM fav ORDER BY id DESC")
	fun getFav(): LiveData<List<FavEntity>>

//	@Query("SELECT * FROM fav where bookmarked = 1")
//	fun getBookmarkedNews(): LiveData<List<FavEntity>>

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	fun insert(fav: FavEntity)

	@Update
	fun updateNews(news: FavEntity)

	@Query("DELETE FROM fav WHERE id = 0")
	fun deleteAll()

//	@Query("SELECT EXISTS(SELECT * FROM fav WHERE title = :title AND bookmarked = 1)")
//	fun isNewsBookmarked(title: String): Boolean
}