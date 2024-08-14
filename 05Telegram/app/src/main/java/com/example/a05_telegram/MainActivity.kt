package com.example.a05_telegram

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configurar Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Configurar DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout)

        // Configurar Toggle para abrir/cerrar el Drawer
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Configurar el listener para el NavigationView
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        // Configurar RecyclerView para mostrar "estados" en la parte superior
        val recyclerViewStates = findViewById<RecyclerView>(R.id.recyclerViewStates)
        recyclerViewStates.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewStates.adapter = StateAdapter(getStates())

        // Configurar RecyclerView para conversaciones de chat
        val recyclerViewChats = findViewById<RecyclerView>(R.id.recyclerViewChats)
        recyclerViewChats.layoutManager = LinearLayoutManager(this)
        recyclerViewChats.adapter = ChatAdapter(getChats())
    }

    private fun getStates(): List<State> {
        // Datos "quemados" para mostrar en la sección de estados
        return listOf(
            State(R.drawable.profile_placeholder, "Mi Estado"),
            State(R.drawable.profile_placeholder, "Estado de Juan"),
            State(R.drawable.profile_placeholder, "Estado de María"),
            State(R.drawable.profile_placeholder, "Estado de Carlos"),
            State(R.drawable.profile_placeholder, "Estado de Andrea"),
            State(R.drawable.profile_placeholder, "Estado de Sofía"),
            State(R.drawable.profile_placeholder, "Estado de Luis")
        )
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_new_group -> {
                // Acción para Nuevo grupo
            }
            // Manejar otras acciones del menú aquí...
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
