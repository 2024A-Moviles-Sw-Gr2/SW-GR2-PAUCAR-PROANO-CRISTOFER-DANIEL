package com.example.a2024aswgr2cdpp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class FRecyclerViewAdaptadorNombreDescripcion(
    private val contexto: FRecyclerView,
    private val lista: ArrayList<BEntrenador>,
    private val recyclerView: RecyclerView
) :RecyclerView.Adapter<FRecyclerViewAdaptadorNombreDescripcion.MyViewHolder>(

){
    inner class MyViewHolder(view: View
    ): RecyclerView.ViewHolder(view){
        val nombreTextView: TextView
        val descripcionTextView:TextView
        val likesTextView: TextView
        val accionButton: TextView
        var numeroLikes = 0
        init {
            nombreTextView= view.findViewById(R.id.tv_nombre)
            descripcionTextView= view.findViewById(R.id.tv_descripcion)
            likesTextView = view.findViewById(R.id.tv_likes)
            accionButton=view.findViewById(R.id.btn_dar_like)
            accionButton.setOnClickListener{añadirLikes()}
        }
        fun añadirLikes(){
            numeroLikes = numeroLikes + 1
            likesTextView.text=numeroLikes.toString()
            contexto.aumentarTotalLikes()
        }
    }

    override fun onCreateViewHolder(parent:ViewGroup, viewType: Int): MyViewHolder {
        val itemView= LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recycle_view_vista,parent,false)
        return  MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return this.lista.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val entrenadoractual = this.lista[position]
        holder.nombreTextView.text=entrenadoractual.nombre
        holder.descripcionTextView.text=entrenadoractual.descripcion
        holder.likesTextView.text=holder.numeroLikes.toString()
        holder.accionButton.text="ID: ${entrenadoractual.id}"+ "Nombre: ${entrenadoractual.nombre}"
    }
}