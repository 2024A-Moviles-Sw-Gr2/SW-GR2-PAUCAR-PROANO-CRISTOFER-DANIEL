package com.example.a2024aswgr2cdpp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    fun mostrarSnackBar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.id_lyout_main),
            texto,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }

    val callbackContenidoIntentExplicito=
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode== Activity.RESULT_OK){
                if(result.data != null){
                    //Logical negocio
                    val data = result.data;
                    mostrarSnackBar(
                        "${data?.getStringExtra("nombreModificado")}"
                    )
                }
            }
        }
    val callbackContenidoIntentImplicito=
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode==Activity.RESULT_OK){
                if(result.data != null){
                    //Logical negocio
                    if (result.data!!.data!=null){

                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val botonCicloVida = findViewById<Button>(
            R.id.btn_ciclo_vida
        )
        botonCicloVida.setOnClickListener {
            irActividad(ACicloVida::class.java)
        }
        val botonIrListView = findViewById<Button>(
            R.id.btn_ir_list_view
        )
        botonIrListView.setOnClickListener {
            irActividad(BListView::class.java)
        }
        val botonIntentImplicito= findViewById<Button>(
            R.id.btn_ir_intent_implicito
        )
        botonIntentImplicito.setOnClickListener {
            val intentConRespuesta= Intent(
                Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            )
            callbackContenidoIntentImplicito.launch(intentConRespuesta)
        }
        val botonIntentExplicito = findViewById<Button>(
            R.id.btn_ir_intent_explicito
        )
        botonIntentExplicito.setOnClickListener {
            val intentExplicito = Intent(
                this,
                CIntent_Explicito_Parametros::class.java
            )
            intentExplicito.putExtra("nombre","Adrian")
            intentExplicito.putExtra("apellido","Eguez")
            intentExplicito.putExtra("edad","34")
            intentExplicito.putExtra("entrenador", BEntrenador(10, "Adrian", "Eguez"))
            callbackContenidoIntentExplicito.launch(intentExplicito)
        }

    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this,clase)
        startActivity(intent)
    }


}