package com.example.workmanagerdemo.db


import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.workmanagerdemo.model.DateTimeData

@Dao
interface DateTimeDAO {

    @Insert
    suspend fun insertDateTime(dateTimeData: DateTimeData): Long

    @Update
    suspend fun updateDateTime(dateTimeData: DateTimeData): Int

    @Delete
    suspend fun deleteDateTime(dateTimeData: DateTimeData): Int

    @Query("DELETE FROM datetime_data_table")
    suspend fun deleteAll(): Int

    @Query("SELECT * FROM datetime_data_table")
    fun getAllDateTimeData(): LiveData<List<DateTimeData>>
}