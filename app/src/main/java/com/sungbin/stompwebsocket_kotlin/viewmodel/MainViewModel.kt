package com.sungbin.stompwebsocket_kotlin.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.sungbin.stompwebsocket_kotlin.vo.MessageVO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent

class MainViewModel : ViewModel() {
    private val TAG = MainViewModel::class.java.simpleName

    private val SOCKET_URL = "wss://varlos-smartwork.com/websocket" // http = ws로 시작하며 https = wss로 시작
    private val MSSAGE_DESTINATION = "/socket/message" // 소켓 주소

    private lateinit var mStompClient: StompClient
    private val gson = Gson()

    private val _message = MutableLiveData<MessageVO>()
    val message: LiveData<MessageVO>
        get() = _message

    fun connectStomp(room: String) {
        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, SOCKET_URL)
        mStompClient.lifecycle().subscribe { lifecycleEvent: LifecycleEvent ->
            when (lifecycleEvent.type) {
                LifecycleEvent.Type.OPENED -> Log.i(
                    TAG,
                    "Stomp connection opened"
                )
                LifecycleEvent.Type.ERROR -> {
                    Log.i(TAG, "Error", lifecycleEvent.exception)
                    connectStomp(room)
                }
                LifecycleEvent.Type.CLOSED -> Log.i(
                    TAG,
                    "Stomp connection closed"
                )
                LifecycleEvent.Type.FAILED_SERVER_HEARTBEAT -> Log.i(
                    TAG,
                    "FAILED_SERVER_HEARTBEAT"
                )
            }
        }
        mStompClient.topic(MSSAGE_DESTINATION + "/" + room)
            .subscribe { stompMessage ->
                Log.d(TAG, "receive messageData :" + stompMessage.payload)
                val messageVO = gson.fromJson(stompMessage.payload, MessageVO::class.java)
                _message.postValue(messageVO)
            }
        mStompClient.connect()
    }

    fun sendMessage(name: String, content: String, time: String, room: String) {   // 구독 하는 방과 같은 주소로 메세지 전송
        val messageVO = MessageVO(name, content, time)
        val messageJson: String = gson.toJson(messageVO)
        mStompClient.send(MSSAGE_DESTINATION + "/" + room, messageJson).subscribe()
        Log.d(TAG, "send messageData : $messageJson")
    }

    fun disconnectStomp(){
        mStompClient.disconnect()
    }

}