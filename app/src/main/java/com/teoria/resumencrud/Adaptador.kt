package com.teoria.resumencrud

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class Adaptador(val context: Context,listIncidencias:ArrayList<Incidencias>):
    RecyclerView.Adapter<IncidenciaViewHolder>() {
    //declarar variables globales
    var listIncidencias:ArrayList<Incidencias>
    val db:DbHelper

    init {
        this.listIncidencias=listIncidencias
        db= DbHelper(context)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncidenciaViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.list_layout,parent,false)
        return IncidenciaViewHolder(view)

    }

    override fun onBindViewHolder(holder: IncidenciaViewHolder, position: Int) {
        val incidencia=listIncidencias[position]
        println("Esta viendo")
        holder.tvCodigo.text=incidencia.codigo
        holder.tvNombre.text=incidencia.nombre
        holder.tvFacturacion.text=incidencia.descripcion.toString()
        // byteArray to image view
        val imageByteArray=incidencia.foto
        val imageBitmap= BitmapFactory.decodeByteArray(imageByteArray,0,imageByteArray.size)
        holder.imagenFoto.setImageBitmap(imageBitmap)
        var misma = BlankFragment2()
        holder.imagenEditar.setOnClickListener(){

            //mostrar edition fragment
            var editionFragment= EditionFragment()

            var bundle= Bundle()
            bundle.putString("codigo",incidencia.codigo)
            bundle.putString("nombre",incidencia.nombre)
            bundle.putString("descripcion",incidencia.descripcion)
            bundle.putByteArray("foto",incidencia.foto)
            editionFragment.arguments=bundle
            var transaction= (context as MainActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView2,editionFragment)
            transaction.commit()
            //db.editIncidencia(incidencia.codigo, incidencia.nombre, incidencia.descripcion)
        }
        holder.imagenEliminar.setOnClickListener(){
            db.deleteIncidencia(incidencia.codigo.toInt())
            //mensaje
            Toast.makeText(context,"Eliminado.",Toast.LENGTH_SHORT).show()
            //cambiar de fragment
            var transaction= (context as MainActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView2,misma)
            transaction.commit()


        }
    }

    override fun getItemCount(): Int {
       return listIncidencias.size
    }


}//cierra Adaptador


