package com.sungbin.stompwebsocket_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.sungbin.stompwebsocket_kotlin.databinding.ActivityMainBinding
import com.sungbin.stompwebsocket_kotlin.viewmodel.MainViewModel
import com.sungbin.stompwebsocket_kotlin.vo.MessageVO

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName

    private lateinit var binding: ActivityMainBinding

    private val viewmodel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            vm = viewmodel
            lifecycleOwner = this@MainActivity
        }

        val intent = intent

        Log.d(TAG, "room : ${intent.getStringExtra("room")}")
        Log.d(TAG, "name : ${intent.getStringExtra("name")}")
    }
}