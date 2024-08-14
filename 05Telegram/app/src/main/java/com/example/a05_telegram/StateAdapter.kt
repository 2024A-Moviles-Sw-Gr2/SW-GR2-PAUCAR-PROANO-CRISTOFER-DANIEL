package com.example.a05_telegram

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StateAdapter(private val stateList: List<State>) : RecyclerView.Adapter<StateAdapter.StateViewHolder>() {

    // ViewHolder para el RecyclerView de estados
    class StateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.state_image)
        val textView: TextView = itemView.findViewById(R.id.state_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_state, parent, false)
        return StateViewHolder(view)
    }

    override fun onBindViewHolder(holder: StateViewHolder, position: Int) {
        val state = stateList[position]
        holder.imageView.setImageResource(state.imageResId)
        holder.textView.text = state.name
    }

    override fun getItemCount() = stateList.size
}
