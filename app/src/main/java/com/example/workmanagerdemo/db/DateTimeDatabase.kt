package com.example.workmanagerdemo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.workmanagerdemo.model.DateTimeData

@Database(entities = [DateTimeData::class], version = 1)
abstract class DateTimeDatabase : RoomDatabase() {
    abstract val dateTimeDAO: DateTimeDAO

    companion object {
        @Volatile
        private var INSTANCE: DateTimeDatabase? = null
        fun getInstance(context: Context): DateTimeDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DateTimeDatabase::class.java,
                        "datetime_data_database"
                    ).build()
                }
                return instance
            }
        }
    }
}