package com.dicodingsubmit.githubuser.data.local

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicodingsubmit.githubuser.data.local.entity.HistoryEntity
import com.dicodingsubmit.githubuser.data.local.room.dao.HistoryDao
import com.dicodingsubmit.githubuser.data.local.room.database.FavDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HistoryRepo(application: Application) {

	private val historyDao: HistoryDao
	private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

	init {
		val db = FavDatabase.getDatabase(application)
		historyDao = db.historyDao()
	}

	fun getAll(): LiveData<List<HistoryEntity>> = historyDao.getAll()

	fun insert(historyEntity: HistoryEntity) = executorService.execute {
		historyDao.insert(historyEntity)
	}

	fun delete(historyEntity: HistoryEntity) = executorService.execute {
		historyDao.delete(historyEntity)
	}

}