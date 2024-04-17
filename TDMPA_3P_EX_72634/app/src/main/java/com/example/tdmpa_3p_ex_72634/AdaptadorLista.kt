package com.example.tdmpa_3p_ex_72634

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import java.util.Timer
import java.util.TimerTask

class AdaptadorLista (private val context: Activity, private val lista: MutableList<AgendaModel>): ArrayAdapter<AgendaModel>(context, R.layout.item_lista, lista) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.item_lista, null, true)

        val txtTituloItem = rowView.findViewById(R.id.txtTituloItem) as TextView
        val txtDescripcionItem = rowView.findViewById(R.id.txtDescripcionItem) as TextView
        val txtRecordatorioItem = rowView.findViewById(R.id.txtRecordatorioItem) as TextView
        val txtEstadoItem = rowView.findViewById(R.id.txtEstadoItem) as TextView
        val btnEditar = rowView.findViewById(R.id.btnEditar) as Button
        val btnBorrar = rowView.findViewById(R.id.btnBorrar) as Button
        val checkItem = rowView.findViewById(R.id.checkItem) as CheckBox

        val item = lista[position]
        txtTituloItem.text = item.titulo
        txtDescripcionItem.text = item.descripcion
        txtRecordatorioItem.text = item.recordatorio
        txtEstadoItem.text = item.estado

        btnEditar.setOnClickListener {
            val flag = false
            val intento = Intent(context, ObjetosActivity::class.java)
            intento.putExtra("flag", flag)
            intento.putExtra("titulo", item.titulo)
            context.startActivity(intento)
        }

        btnBorrar.setOnClickListener {
            val db = DatabaseAgenda(context)
            db.deleteAgenda(item.titulo)
            lista.removeAt(position)
            notifyDataSetChanged()
        }

        checkItem.setOnCheckedChangeListener { buttonView, isChecked ->
            txtEstadoItem.text = "Completado"

            Timer().schedule(object : TimerTask() {
                override fun run() {
                    context.runOnUiThread{
                        val db = DatabaseAgenda(context)
                        db.deleteAgenda(item.titulo)
                        lista.removeAt(position)
                        notifyDataSetChanged()
                    }
                }
            }, 1000L)
        }

        return rowView
    }
}