package com.dicodingsubmit.githubuser.data.local

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicodingsubmit.githubuser.data.local.entity.FavEntity
import com.dicodingsubmit.githubuser.data.local.room.dao.FavDao
import com.dicodingsubmit.githubuser.data.local.room.database.FavDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavRepo(application: Application) {

	private val favDao: FavDao
	private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

	init {
		val db = FavDatabase.getDatabase(application)
		favDao = db.favDao()
	}

	fun getFav(): LiveData<List<FavEntity>> = favDao.getFav()

	fun insertFav(fav: FavEntity) = executorService.execute {
		favDao.insert(fav)
	}

}