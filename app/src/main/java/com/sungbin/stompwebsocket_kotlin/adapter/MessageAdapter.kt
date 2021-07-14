package com.sungbin.stompwebsocket_kotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sungbin.stompwebsocket_kotlin.MainActivity
import com.sungbin.stompwebsocket_kotlin.databinding.ItemMyBinding
import com.sungbin.stompwebsocket_kotlin.databinding.ItemOtherBinding
import com.sungbin.stompwebsocket_kotlin.vo.MessageVO

class MessageAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var mList = mutableListOf<MessageVO>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            MainActivity.MY -> {
                val binding =
                    ItemMyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return MyViewHolder(binding)
            }
            MainActivity.OTHER -> {
                val binding =
                    ItemOtherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return OtherViewHolder(binding)
            }
            else -> {
                val binding =
                    ItemMyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val messageVO = mList[position]

        if (holder is MyViewHolder) holder.bind(messageVO)
        else (holder as OtherViewHolder).bind(messageVO)

    }

    fun add(messageVO: MessageVO) {
        mList.add(messageVO)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return mList[position].type!! // 메세지 객체 타입 비교 후 홀더 구분
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class MyViewHolder(val binding: ItemMyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(messageVO: MessageVO){
            binding.message = messageVO
        }
    }

    inner class OtherViewHolder(val binding: ItemOtherBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(messageVO: MessageVO){
            binding.message = messageVO
        }
    }
}