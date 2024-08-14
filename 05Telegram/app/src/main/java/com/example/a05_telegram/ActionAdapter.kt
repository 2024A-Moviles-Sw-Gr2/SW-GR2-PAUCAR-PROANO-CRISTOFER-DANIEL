package com.example.a05_telegram

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ActionAdapter(private val actionList: List<Action>) : RecyclerView.Adapter<ActionAdapter.ActionViewHolder>() {

    class ActionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val actionIconView: ImageView = itemView.findViewById(R.id.action_icon)
        val actionTextView: TextView = itemView.findViewById(R.id.action_text)
        val actionContainer: View = itemView.findViewById(R.id.action_container) // Contenedor para cambiar el fondo
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_action, parent, false)
        return ActionViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActionViewHolder, position: Int) {
        val action = actionList[position]
        holder.actionIconView.setImageResource(action.icon)
        holder.actionTextView.text = action.text

        // Cambiar el color de fondo si la acción es "Chats"
        if (action.text == "Chats") {
            holder.actionContainer.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.camera_button_background_color))
        } else {
            holder.actionContainer.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, android.R.color.transparent))
        }
    }

    override fun getItemCount() = actionList.size
}
