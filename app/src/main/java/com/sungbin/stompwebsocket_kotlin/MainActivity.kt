package com.sungbin.stompwebsocket_kotlin

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sungbin.stompwebsocket_kotlin.adapter.MessageAdapter
import com.sungbin.stompwebsocket_kotlin.databinding.ActivityMainBinding
import com.sungbin.stompwebsocket_kotlin.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName

    private val viewmodel: MainViewModel by viewModels()

    companion object{
        val MY = 1
        val OTHER = 2
    }

    private lateinit var binding: ActivityMainBinding

    private lateinit var room: String
    private lateinit var name: String

    private lateinit var adapter: MessageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            vm = viewmodel
            lifecycleOwner = this@MainActivity
        }
        initRecycler()

        val intent = intent
        room = intent.getStringExtra("room").toString()
        name = intent.getStringExtra("name").toString()

        viewmodel.connectStomp(room)

        viewmodel.message.observe(this, Observer { it ->
            if(it.name.equals(name)) it.type = MY else it.type = OTHER          // 메세지 보낸 사람 구분
            adapter.add(it)
            binding.recyclerview.smoothScrollToPosition(adapter.itemCount)
        })

        binding.sendBtn.setOnClickListener {
            val content = binding.messageEdit.text.toString()

            if (!TextUtils.isEmpty(content)) {
                sendMessage(name, content, room)
                binding.messageEdit.text.clear()
            } else {
                Toast.makeText(this, "메세지를 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }

    }
    fun initRecycler(){
        adapter = MessageAdapter()
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
    }

    fun sendMessage(name: String, content: String, room: String) {
        viewmodel.sendMessage(name, content, room)
    }

    override fun onBackPressed() {
        viewmodel.disconnectStomp()
        super.onBackPressed()
    }
}