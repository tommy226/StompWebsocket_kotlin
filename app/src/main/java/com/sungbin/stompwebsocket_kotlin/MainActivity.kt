package com.sungbin.stompwebsocket_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.sungbin.stompwebsocket_kotlin.databinding.ActivityMainBinding
import com.sungbin.stompwebsocket_kotlin.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName

    private val viewmodel: MainViewModel by viewModels()

    private val MY = 1
    private val OTHER = 2

    private lateinit var binding: ActivityMainBinding

    private lateinit var room: String
    private lateinit var name: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            vm = viewmodel
            lifecycleOwner = this@MainActivity
        }
        val intent = intent
        room = intent.getStringExtra("room").toString()
        name = intent.getStringExtra("name").toString()

        viewmodel.connectStomp(room)

        viewmodel.message.observe(this, Observer { it ->
            if(it.name.equals(name)) it.type = MY else it.type = OTHER          // 메세지 보낸 사람 구분
            Log.d(TAG, "Message : ${it}")
        })

    }

    override fun onBackPressed() {
        viewmodel.disconnectStomp()
        super.onBackPressed()
    }
}