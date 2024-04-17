package com.example.tdmpa_3p_ex_72634

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast

class ObjetosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_objetos)

        val db = DatabaseAgenda(this)

        val btnVolver = findViewById<Button>(R.id.btnVolver)
        btnVolver.setOnClickListener {
            finish()
        }

        val spnTipos = findViewById<Spinner>(R.id.spnTipos)
        val tipos = resources.getStringArray(R.array.tipos)
        spnTipos.adapter = ArrayAdapter(this@ObjetosActivity,android.R.layout.simple_spinner_item,tipos)
        var tipoSeleccionado = ""

        spnTipos.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                tipoSeleccionado = tipos[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@ObjetosActivity, "No seleccionó un tipo", Toast.LENGTH_SHORT).show()
            }
        }

        val txtTitulo = findViewById<EditText>(R.id.txtTitulo)
        val txtDescripcion = findViewById<EditText>(R.id.txtDescripcion)
        val txtRecordatorio = findViewById<EditText>(R.id.txtRecordatorio)
        val btnCrearModificar = findViewById<Button>(R.id.btnCrearModificar)

        val flag = intent.getBooleanExtra("flag", true)
        val titulo = intent.getStringExtra("titulo")
        var retrivedAgenda = db.obtenerAgendaPorTitulo(titulo.toString())

        if (flag == false){
            btnCrearModificar.text = "Actualizar"

            tipoSeleccionado = retrivedAgenda?.tipo.toString()
            val adapter = spnTipos.adapter
            var selectedItemPosition = 0
            for (i in 0 until adapter.count) {
                if (adapter.getItem(i).toString().equals(tipoSeleccionado)) {
                    selectedItemPosition = i
                    break
                }
            }
            if (selectedItemPosition != -1) {
                spnTipos.setSelection(selectedItemPosition)
            }

            txtTitulo.setText(retrivedAgenda?.titulo)
            txtDescripcion.setText(retrivedAgenda?.descripcion)
            txtRecordatorio.setText(retrivedAgenda?.recordatorio)
        }else{
            btnCrearModificar.text = "Crear"
        }


        btnCrearModificar.setOnClickListener {
            if(btnCrearModificar.text.equals("Crear")){
                if(validarCampos()){
                    val agendaModel = AgendaModel(
                        0,
                        tipoSeleccionado,
                        txtTitulo.text.toString(),
                        txtDescripcion.text.toString(),
                        txtRecordatorio.text.toString(),
                        "No Completado"
                    )
                    db.addAgenda(agendaModel)
                    Toast.makeText(this@ObjetosActivity, "$tipoSeleccionado Agregad@", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }else{
                if (validarCampos()){
                    val agendaModel = AgendaModel(
                        retrivedAgenda?.id.toString().toInt(),
                        tipoSeleccionado,
                        txtTitulo.text.toString(),
                        txtDescripcion.text.toString(),
                        txtRecordatorio.text.toString(),
                        "No Completado"
                    )
                    db.updateAgenda(agendaModel)
                    Toast.makeText(this@ObjetosActivity,"Agenda Actualizada", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }

    }

    fun validarCampos(): Boolean {
        val txtTitulo = findViewById<EditText>(R.id.txtTitulo)
        val txtDescripcion = findViewById<EditText>(R.id.txtDescripcion)
        val txtRecordatorio = findViewById<EditText>(R.id.txtRecordatorio)

        when {
            txtTitulo.text.isEmpty() -> {
                Toast.makeText(this@ObjetosActivity, "Campo Titulo vacio", Toast.LENGTH_SHORT).show()
                return false
            }
            txtDescripcion.text.isEmpty() -> {
                Toast.makeText(this@ObjetosActivity, "Campo Descripciom vacio", Toast.LENGTH_SHORT).show()
                return false
            }
            txtRecordatorio.text.isEmpty() -> {
                Toast.makeText(this@ObjetosActivity, "Campo Recordatorio vacio", Toast.LENGTH_SHORT).show()
                return false
            }
            else -> return true
        }
    }
}