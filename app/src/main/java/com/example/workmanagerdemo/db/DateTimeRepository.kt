package com.example.workmanagerdemo.db

import com.example.workmanagerdemo.model.DateTimeData

class DateTimeRepository(private val dao: DateTimeDAO) {
    val dateTimeData = dao.getAllDateTimeData()

    suspend fun insert(dateTimeData: DateTimeData): Long {
        return dao.insertDateTime(dateTimeData)
    }

    suspend fun update(dateTimeData: DateTimeData): Int {
        return dao.updateDateTime(dateTimeData)
    }

    suspend fun delete(dateTimeData: DateTimeData): Int {
        return dao.deleteDateTime(dateTimeData)
    }

    suspend fun deleteAll(): Int {
        return dao.deleteAll()
    }
}