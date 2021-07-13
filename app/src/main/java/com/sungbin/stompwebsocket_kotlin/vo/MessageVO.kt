package com.sungbin.stompwebsocket_kotlin.vo

data class MessageVO(val name: String, val content: String, val time: String, var type: Int?= null) {

}
