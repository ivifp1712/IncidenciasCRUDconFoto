package com.teoria.resumencrud

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap

class DbHelper(context: Context):SQLiteOpenHelper
    (context,"base.db",null,1) {

    companion object{
        private val TABLE="incidencias"
        private val ID="id"
        private val NOMBRE="nombre"
        private val INCIDENCIA="incidencia"
        private val FOTO="foto"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val CREAR_TABLA_INCIDENCIAS = ("CREATE TABLE " + TABLE + "("
                + ID + " INTEGER PRIMARY KEY," + NOMBRE + " TEXT,"
                + INCIDENCIA + " INTEGER," + FOTO + " BLOB" + ")")
        p0?.execSQL(CREAR_TABLA_INCIDENCIAS)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS " + TABLE)
        onCreate(p0)
    }

    fun addIncidencia(incidencia:Incidencias){
        val db=this.writableDatabase
        val query="INSERT INTO $TABLE VALUES (?,?,?,?)"
        val statement=db.compileStatement(query)
        statement.bindString(1,incidencia.codigo)
        statement.bindString(2,incidencia.nombre)
        statement.bindString(3,incidencia.descripcion)
        statement.bindBlob(4,incidencia.foto)
        statement.executeInsert()
        print("llamando a insertar Incidencias")

    }
    fun editIncidencia(codigo: String, nombre:String, descripcion:String, foto:ByteArray){
        print("llamando a editar Incidencias")
        val db=this.writableDatabase
        val query="UPDATE $TABLE SET $NOMBRE=?, $INCIDENCIA=?, $FOTO=? WHERE $ID=?"
        val statement=db.compileStatement(query)
        statement.bindString(1,nombre)
        statement.bindString(2,descripcion)
        statement.bindBlob(3,foto)
        statement.bindString(4,codigo)
        statement.executeUpdateDelete()
    }
    fun deleteIncidencia(codigo: Int){
        print("llamando a eliminar Incidencias")
        var sql = "DELETE FROM $TABLE WHERE $ID = $codigo"
        val db=this.writableDatabase
        db.execSQL(sql)
    }
    @SuppressLint("SuspiciousIndentation")
    fun viewIncidencia():ArrayList<Incidencias>{
        print("llamando a verIncidencias")
        val sql = "select * from $TABLE"
        val db = this.readableDatabase
        val storeIncidencias =ArrayList<Incidencias>()
        val cursor = db.rawQuery(sql, null)
            if (cursor.moveToFirst()) {
                do {
                    val codigo = cursor.getString(0)
                    val nombre = cursor.getString(1)
                    val incidencia = cursor.getString(2).toString()
                    val foto = cursor.getBlob(3)
                    storeIncidencias.add(Incidencias(codigo, nombre, incidencia, foto))
                }
                while (cursor.moveToNext())
            }
            cursor.close()
            return storeIncidencias
    }

}//cierra clase DbHelper