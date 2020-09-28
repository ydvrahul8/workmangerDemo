package com.example.workmanagerdemo.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workmanagerdemo.db.DateTimeRepository
import com.example.workmanagerdemo.model.DateTimeData
import com.example.workmanagerdemo.utils.Event
import kotlinx.coroutines.launch

class MainViewModel(private val repository: DateTimeRepository) : ViewModel() {

    val dateTimeData = repository.dateTimeData

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    fun insert(dateTimeData: DateTimeData) {
        viewModelScope.launch {
            val newRowId = repository.insert(dateTimeData)
            if (newRowId > -1)
                statusMessage.value = Event("DateTimeData Inserted Successfully $newRowId.")
            else
                statusMessage.value = Event("Error Occurred.")
        }
    }

    fun update(dateTimeData: DateTimeData) {
        viewModelScope.launch {
            val noOfRows = repository.update(dateTimeData)
            if (noOfRows > 0) {
                statusMessage.value = Event("$noOfRows Rows Updated Successfully.")
            } else
                statusMessage.value = Event("Error Occurred.")
        }
    }

    fun delete(dateTimeData: DateTimeData) {
        viewModelScope.launch {
            val noOfRowsDeleted = repository.delete(dateTimeData)
            if (noOfRowsDeleted > 0) {
                statusMessage.value = Event("$noOfRowsDeleted rows Deleted Successfully.")
            } else
                statusMessage.value = Event("Error Occurred.")
        }
    }

    fun clearAll() {
        viewModelScope.launch {
            val noOfRewsDeleted = repository.deleteAll()
            if (noOfRewsDeleted > 0)
                statusMessage.value = Event("$noOfRewsDeleted Rows Deleted Successfully.")
            else
                statusMessage.value = Event("Error Occurred.")
        }
    }
}