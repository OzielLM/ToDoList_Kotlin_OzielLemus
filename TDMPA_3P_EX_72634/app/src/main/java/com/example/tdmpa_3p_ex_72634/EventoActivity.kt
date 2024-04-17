package com.example.tdmpa_3p_ex_72634

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class EventoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evento)

        generarLosItemsEnLaLista()

        val btnVolverEvento = findViewById<Button>(R.id.btnVolverEvento)
        val btnAñadirEvento = findViewById<Button>(R.id.btnAñadirEvento)

        btnVolverEvento.setOnClickListener {
            finish()
        }

        btnAñadirEvento.setOnClickListener {
            val flag = true
            val intento = Intent(this@EventoActivity, ObjetosActivity::class.java)
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
        val listaEstado = findViewById<ListView>(R.id.lista)
        val itemsEstado = db.obtenerAgendaPorTipo("Evento")
        val adaptador = AdaptadorLista(this, itemsEstado)
        listaEstado.adapter = adaptador
    }
}