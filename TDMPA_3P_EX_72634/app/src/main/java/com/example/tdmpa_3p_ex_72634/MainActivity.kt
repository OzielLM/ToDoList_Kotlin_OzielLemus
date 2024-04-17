package com.example.tdmpa_3p_ex_72634

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        agendarNotis()

        val vwBlancoEvento = findViewById<View>(R.id.vwBlancoEvento)
        val vwRojoEvento = findViewById<View>(R.id.vwRojoEvento)
        val vwBlancoNota = findViewById<View>(R.id.vwBlancoNota)
        val vwAmarilloNota = findViewById<View>(R.id.vwAmarilloNota)
        val vwBlancoTarea = findViewById<View>(R.id.vwBlancoTarea)
        val vwMoradoTarea = findViewById<View>(R.id.vwMoradoTarea)

        vwBlancoEvento.setOnClickListener {
            val intento = Intent(this@MainActivity, EventoActivity::class.java)
            startActivity(intento)
        }

        vwRojoEvento.setOnClickListener {
            val intento = Intent(this@MainActivity, EventoActivity::class.java)
            startActivity(intento)
        }

        vwBlancoNota.setOnClickListener {
            val intento = Intent(this@MainActivity, NotaActivity::class.java)
            startActivity(intento)
        }

        vwAmarilloNota.setOnClickListener {
            val intento = Intent(this@MainActivity, NotaActivity::class.java)
            startActivity(intento)
        }

        vwBlancoTarea.setOnClickListener {
            val intento = Intent(this@MainActivity, TareaActivity::class.java)
            startActivity(intento)
        }

        vwMoradoTarea.setOnClickListener {
            val intento = Intent(this@MainActivity, TareaActivity::class.java)
            startActivity(intento)
        }

        val btnAñadir = findViewById<Button>(R.id.btnAñadir)

        btnAñadir.setOnClickListener {
            val flag = true
            val intento = Intent(this@MainActivity, ObjetosActivity::class.java)
            intento.putExtra("flag", flag)
            startActivity(intento)
        }


        contarElementosDeLosObjetos()
    }

    override fun onResume() {
        super.onResume()
        contarElementosDeLosObjetos()
        agendarNotis()
    }

    fun contarElementosDeLosObjetos(){
        val txtNumEvento = findViewById<TextView>(R.id.txtNumEvento)
        val txtNumNota = findViewById<TextView>(R.id.txtNumNota)
        val txtNumTarea = findViewById<TextView>(R.id.txtNumTarea)

        val db = DatabaseAgenda(this)

        txtNumEvento.text = db.countAgendaPorTipo("Evento").toString()
        txtNumNota.text = db.countAgendaPorTipo("Nota").toString()
        txtNumTarea.text = db.countAgendaPorTipo("Tarea").toString()
    }

    fun esHoyLaFechaDelRecordatorio(fechaRecordatorio: String): Boolean {
        val fechaActual = LocalDate.now()
        val formato = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val fechaRecordatorioParsed = LocalDate.parse(fechaRecordatorio, formato)
        return fechaActual.isEqual(fechaRecordatorioParsed)
    }

    fun agendarNotis(){
        val db = DatabaseAgenda(this)

        var listaElementos = db.obtenerTodosLosElementos()

        for (i in 0 until listaElementos.count()){
            if (esHoyLaFechaDelRecordatorio(listaElementos[i].recordatorio)) {
                Toast.makeText(this@MainActivity, "Recordatorio: ${listaElementos[i].titulo}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}