package com.example.tdmpa_3p_ex_72634

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.Date

class DatabaseAgenda(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    companion object{
        private const val DATABASE_NAME = "AgendaDB"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "AgendaTable"
        private const val KEY_ID = "_id"
        private const val KEY_TIPO = "tipo"
        private const val KEY_TITULO = "titulo"
        private const val KEY_DESCRIPCION = "descripcion"
        private const val KEY_RECORDATORIO = "recordatorio"
        private const val KEY_ESTADO = "estado"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = (
                "CREATE TABLE $TABLE_NAME (" +
                        "$KEY_ID INTEGER PRIMARY KEY, " +
                        "$KEY_TIPO TEXT, " +
                        "$KEY_TITULO TEXT, " +
                        "$KEY_DESCRIPCION TEXT, " +
                        "$KEY_RECORDATORIO TEXT, " +
                        "$KEY_ESTADO TEXT);"
                )
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addAgenda(agendaModel: AgendaModel){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_TIPO, agendaModel.tipo)
        values.put(KEY_TITULO, agendaModel.titulo)
        values.put(KEY_DESCRIPCION, agendaModel.descripcion)
        values.put(KEY_RECORDATORIO, agendaModel.recordatorio)
        values.put(KEY_ESTADO, agendaModel.estado)
        db.insert(TABLE_NAME, null, values)
    }

    fun updateAgenda(agendaModel: AgendaModel){
        val db = this.writableDatabase
        val  values = ContentValues()
        values.put(KEY_TIPO, agendaModel.tipo)
        values.put(KEY_TITULO, agendaModel.titulo)
        values.put(KEY_DESCRIPCION, agendaModel.descripcion)
        values.put(KEY_RECORDATORIO, agendaModel.recordatorio)
        values.put(KEY_ESTADO, agendaModel.estado)
        db.update(TABLE_NAME, values, "$KEY_ID=?", arrayOf(agendaModel.id.toString()))
    }

    fun deleteAgenda(titulo: String) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$KEY_TITULO = ?", arrayOf(titulo))
        db.close()
    }

    fun countAgendaPorTipo(tipo: String): Int {
        val db = this.readableDatabase
        val select = "SELECT COUNT(*) FROM $TABLE_NAME WHERE $KEY_TIPO = ?"
        val cursor = db.rawQuery(select, arrayOf(tipo))
        if (cursor.moveToFirst()) {
            val count = cursor.getInt(0)
            cursor.close()
            return count
        } else {
            cursor.close()
            return 0
        }
    }

    @SuppressLint("Range")
    fun obtenerAgendaPorTipo(tipo: String): MutableList<AgendaModel> {
        val db = this.readableDatabase
        val titulos = mutableListOf<AgendaModel>()
        val selectQuery = "SELECT $KEY_ID, $KEY_TITULO, $KEY_DESCRIPCION, $KEY_RECORDATORIO, $KEY_ESTADO FROM $TABLE_NAME WHERE $KEY_TIPO = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(tipo))
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                val titulo = cursor.getString(cursor.getColumnIndex(KEY_TITULO))
                val descripcion = cursor.getString(cursor.getColumnIndex(KEY_DESCRIPCION))
                val recordatorio = cursor.getString(cursor.getColumnIndex(KEY_RECORDATORIO))
                val estado = cursor.getString(cursor.getColumnIndex(KEY_ESTADO))
                titulos.add(AgendaModel(id,tipo,titulo,descripcion,recordatorio,estado))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return titulos
    }

    @SuppressLint("Range")
    fun obtenerAgendaPorTitulo(titulo: String): AgendaModel? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(KEY_ID, KEY_TIPO, KEY_TITULO, KEY_DESCRIPCION, KEY_RECORDATORIO, KEY_ESTADO),
            "$KEY_TITULO=?",
            arrayOf(titulo),
            null,
            null,
            null
        )
        return if(cursor.moveToFirst()){
            val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
            val tipo = cursor.getString(cursor.getColumnIndex(KEY_TIPO))
            val descripcion = cursor.getString(cursor.getColumnIndex(KEY_DESCRIPCION))
            val recordatorio = cursor.getString(cursor.getColumnIndex(KEY_RECORDATORIO))
            val estado = cursor.getString(cursor.getColumnIndex(KEY_ESTADO))
            AgendaModel(id, tipo, titulo, descripcion, recordatorio, estado)
        }
        else{
            null
        }
    }

    @SuppressLint("Range")
    fun obtenerTodosLosElementos(): MutableList<AgendaModel> {
        val elementos = mutableListOf<AgendaModel>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val elemento = AgendaModel(
                    id = cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                    tipo = cursor.getString(cursor.getColumnIndex(KEY_TIPO)),
                    titulo = cursor.getString(cursor.getColumnIndex(KEY_TITULO)),
                    descripcion = cursor.getString(cursor.getColumnIndex(KEY_DESCRIPCION)),
                    recordatorio = cursor.getString(cursor.getColumnIndex(KEY_RECORDATORIO)),
                    estado = cursor.getString(cursor.getColumnIndex(KEY_ESTADO)))
                elementos.add(elemento)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()

        return elementos
    }
}

data class AgendaModel(
    val id: Int,
    val tipo: String,
    val titulo: String,
    val descripcion: String,
    val recordatorio: String,
    val estado: String,
)