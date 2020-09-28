package com.example.workmanagerdemo.view.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.workmanagerdemo.R
import com.example.workmanagerdemo.db.DateTimeDatabase
import com.example.workmanagerdemo.db.DateTimeRepository
import com.example.workmanagerdemo.model.DateTimeData
import com.example.workmanagerdemo.utils.MainViewModelFactory
import com.example.workmanagerdemo.view.adapter.TimeDateAdapter
import com.example.workmanagerdemo.view.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: TimeDateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dao = DateTimeDatabase.getInstance(applicationContext).dateTimeDAO
        val repository = DateTimeRepository(dao)
        val factory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        initRecyclerView()
        buttonStart.setOnClickListener {
            saveToDB()
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

    private fun saveToDB() {
        val time = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
        val currentDate = time.format(Date())
        val data = DateTimeData(0, currentDate)
        viewModel.insert(data)
    }
}