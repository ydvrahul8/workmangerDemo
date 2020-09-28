package com.example.workmanagerdemo.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.workmanagerdemo.view.viewmodel.MainViewModel
import com.example.workmanagerdemo.db.DateTimeRepository

class MainViewModelFactory(private val repository: DateTimeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                repository
            ) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}