package com.teoria.resumencrud

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class IncidenciaViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
    var tvNombre:TextView=itemView.findViewById(R.id.tv_nombre)
    var tvFacturacion:TextView=itemView.findViewById(R.id.tv_facturacion)
    var tvCodigo:TextView=itemView.findViewById(R.id.tv_codigo)
    var imagenEditar:ImageView=itemView.findViewById(R.id.editar)
    var imagenEliminar:ImageView=itemView.findViewById(R.id.eliminar)
    var imagenFoto:ImageView=itemView.findViewById(R.id.imagen_foto)

}//cierra class