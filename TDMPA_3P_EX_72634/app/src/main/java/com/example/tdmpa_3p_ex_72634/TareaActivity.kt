package com.example.tdmpa_3p_ex_72634

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView

class TareaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarea)

        generarLosItemsEnLaLista()

        val btnVolverTarea = findViewById<Button>(R.id.btnVolverTarea)
        val btnAñadirTarea = findViewById<Button>(R.id.btnAñadirTarea)

        btnVolverTarea.setOnClickListener {
            finish()
        }

        btnAñadirTarea.setOnClickListener {
            val flag = true
            val intento = Intent(this@TareaActivity, ObjetosActivity::class.java)
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
        val listaTarea = findViewById<ListView>(R.id.lista)
        val itemsTarea = db.obtenerAgendaPorTipo("Tarea")
        val adaptador = AdaptadorLista(this, itemsTarea)
        listaTarea.adapter = adaptador
    }
}