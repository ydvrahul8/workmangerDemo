package com.example.workmanagerdemo.view.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.workmanagerdemo.R
import com.example.workmanagerdemo.db.DateTimeDatabase
import com.example.workmanagerdemo.db.DateTimeRepository
import com.example.workmanagerdemo.utils.MainViewModelFactory
import com.example.workmanagerdemo.view.adapter.TimeDateAdapter
import com.example.workmanagerdemo.view.viewmodel.MainViewModel
import com.example.workmanagerdemo.workmanager.StoreDateTimeWorker
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: TimeDateAdapter
    private lateinit var worker: WorkRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        worker = PeriodicWorkRequest
            .Builder(StoreDateTimeWorker::class.java, 60, TimeUnit.MINUTES)
            .build()
        val dao = DateTimeDatabase.getInstance(applicationContext).dateTimeDAO
        val repository = DateTimeRepository(dao)
        val factory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        initRecyclerView()
        buttonStart.setOnClickListener {
            startWorkRequest()
        }
        buttonStop.setOnClickListener {
            stopWorkRequest()
        }
        viewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initRecyclerView() {
        recyclerView.isNestedScrollingEnabled = true
        recyclerView.setHasFixedSize(true)
        adapter = TimeDateAdapter()
        recyclerView.adapter = adapter
        displayDataFromDB()
    }

    private fun displayDataFromDB() {
        viewModel.dateTimeData.observe(this, Observer {
            if (it.isNullOrEmpty())
                textView_noData.visibility = View.VISIBLE
            else
                textView_noData.visibility = View.GONE
            adapter.setData(it)
        })
    }

    private fun startWorkRequest() {
        WorkManager.getInstance(this).enqueue(worker)
    }

    private fun stopWorkRequest() {
        WorkManager.getInstance(this).cancelAllWork()
    }
}
