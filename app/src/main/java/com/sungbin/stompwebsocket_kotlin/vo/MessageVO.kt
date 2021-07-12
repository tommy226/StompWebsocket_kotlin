package com.sungbin.stompwebsocket_kotlin.vo

data class MessageVO(var name: String, var content: String, var time: String) {

    // 메세지 자신 or 다른사람 구분 위함
    var type = 0

}
