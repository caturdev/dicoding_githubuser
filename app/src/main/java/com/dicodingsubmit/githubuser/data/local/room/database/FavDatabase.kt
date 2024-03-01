package com.dicodingsubmit.githubuser.data.local.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicodingsubmit.githubuser.data.local.entity.FavEntity
import com.dicodingsubmit.githubuser.data.local.entity.HistoryEntity
import com.dicodingsubmit.githubuser.data.local.room.dao.FavDao
import com.dicodingsubmit.githubuser.data.local.room.dao.HistoryDao

@Database(entities = [FavEntity::class, HistoryEntity::class], version = 1)
abstract class FavDatabase : RoomDatabase() {

	abstract fun favDao(): FavDao

	abstract fun historyDao(): HistoryDao

	companion object {
		@Volatile
		private var instance: FavDatabase? = null

		@JvmStatic
		fun getDatabase(context: Context): FavDatabase =
			instance ?: synchronized(FavDatabase::class.java) {
				instance ?: Room.databaseBuilder(
					context.applicationContext,
					FavDatabase::class.java, "database"
				).build()
			}
	}
}