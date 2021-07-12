package com.sungbin.stompwebsocket_kotlin

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.sungbin.stompwebsocket_kotlin.databinding.ActivityJoinBinding

class JoinActivity : AppCompatActivity() {
    private val TAG = JoinActivity::class.java.simpleName

    private lateinit var binding: ActivityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)

        binding.join = this@JoinActivity
    }

    fun joinRoom(){
        val room = binding.joinroomEdit.text.toString()
        val name = binding.nameEdit.text.toString()
        if (!TextUtils.isEmpty(room) && !TextUtils.isEmpty(name)) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("room", room)
            intent.putExtra("name", name)
            startActivity(intent)
        } else {
            Toast.makeText(this, "모두 입력 해주세요", Toast.LENGTH_SHORT).show()
        }
    }
}