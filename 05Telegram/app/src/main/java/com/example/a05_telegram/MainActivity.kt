package com.example.a05_telegram

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configurar RecyclerView para conversaciones de chat
        val recyclerViewChats = findViewById<RecyclerView>(R.id.recyclerViewChats)
        recyclerViewChats.layoutManager = LinearLayoutManager(this)
        recyclerViewChats.adapter = ChatAdapter(getChats())

        // Configurar RecyclerView para acciones (botones)
        val recyclerViewActions = findViewById<RecyclerView>(R.id.recyclerViewActions)
        recyclerViewActions.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewActions.adapter = ActionAdapter(getActions())
    }

    private fun getChats(): List<Chat> {
        // Datos "quemados" para mostrar en la interfaz
        return listOf(
            Chat(R.drawable.profile_placeholder, "PollBot", "Votaste para 'Mate'.", "5:54 PM"),
            Chat(R.drawable.profile_placeholder, "Madame X", "Foto", "1:37 PM"),
            Chat(R.drawable.profile_placeholder, "Sandro Arturo", "Mis apuntes...", "1:12 PM"),
            Chat(R.drawable.profile_placeholder, "Dr. Andrew Porter", "Puedes ser mi acompañante😎", "Mar"),
            Chat(R.drawable.profile_placeholder, "Gabrielle", "Mi padre te mandó una invitación...", "Mar"),
            Chat(R.drawable.profile_placeholder, "Viajeros", "Jhon: Disfrutando la ciudad", "Lun"),
            Chat(R.drawable.profile_placeholder, "Expediciones", "Documento", "Lun")
        )
    }

    private fun getActions(): List<Action> {
        // Datos "quemados" para los botones de acción
        return listOf(
            Action(R.drawable.ic_add, "Chats"),
            Action(R.drawable.ic_add, "Estados"),
            Action(R.drawable.ic_add, "Ajustes")
        )
    }
}
