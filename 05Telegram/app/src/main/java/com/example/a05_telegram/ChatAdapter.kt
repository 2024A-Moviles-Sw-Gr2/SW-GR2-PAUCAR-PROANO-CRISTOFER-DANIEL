package com.example.a05_telegram

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(private val chatList: List<Chat>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImageView: ImageView = itemView.findViewById(R.id.profile_image)
        val nameTextView: TextView = itemView.findViewById(R.id.chat_name)
        val messageTextView: TextView = itemView.findViewById(R.id.chat_message)
        val timeTextView: TextView = itemView.findViewById(R.id.chat_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chat = chatList[position]
        holder.profileImageView.setImageResource(chat.profileImage)
        holder.nameTextView.text = chat.name
        holder.messageTextView.text = chat.message
        holder.timeTextView.text = chat.time
    }

    override fun getItemCount() = chatList.size
}
