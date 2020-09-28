package com.example.workmanagerdemo.workmanager

import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.workmanagerdemo.db.DateTimeDatabase
import com.example.workmanagerdemo.model.DateTimeData
import com.example.workmanagerdemo.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class StoreDateTimeWorker(private val context: Context, params: WorkerParameters) :
    Worker(context, params) {
    override fun doWork(): Result {
        return try {
            GlobalScope.launch(Dispatchers.IO) {
                val time = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
                val currentDate = time.format(Date())
                val data = DateTimeData(0, currentDate)
                DateTimeDatabase.getInstance(context).dateTimeDAO.insertDateTime(data)
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}