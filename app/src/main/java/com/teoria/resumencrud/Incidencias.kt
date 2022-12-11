package com.teoria.resumencrud

class Incidencias{
    var codigo:String
    var nombre:String
    var descripcion:String
    var foto:ByteArray
    internal constructor(codigo:String,nombre:String,facturacion:String, foto:ByteArray){
        this.codigo=codigo
        this.nombre=nombre
        this.descripcion=facturacion
        this.foto=foto
    }

}
