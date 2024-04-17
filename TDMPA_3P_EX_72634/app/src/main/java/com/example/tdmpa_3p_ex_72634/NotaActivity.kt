package com.example.tdmpa_3p_ex_72634

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView

class NotaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nota)

        generarLosItemsEnLaLista()

        val btnVolverNota = findViewById<Button>(R.id.btnVolverNota)
        val btnAñadirNota = findViewById<Button>(R.id.btnAñadirNota)

        btnVolverNota.setOnClickListener {
            finish()
        }

        btnAñadirNota.setOnClickListener {
            val flag = true
            val intento = Intent(this@NotaActivity, ObjetosActivity::class.java)
            intento.putExtra("flag", flag)
            startActivity(intento)
        }
    }

    override fun onResume() {
        super.onResume()
        generarLosItemsEnLaLista()
    }

    fun generarLosItemsEnLaLista(){
        val db = DatabaseAgenda(this)
        val listaNota = findViewById<ListView>(R.id.lista)
        val itemsNota = db.obtenerAgendaPorTipo("Nota")
        val adaptador = AdaptadorLista(this, itemsNota)
        listaNota.adapter = adaptador
    }
}