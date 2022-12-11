package com.teoria.resumencrud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lazday.sharedpreferences.helper.Constant
import com.lazday.sharedpreferences.helper.PrefHelper

class MainActivity : AppCompatActivity() {
    //variables de clase
    private lateinit var prefHelper: PrefHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // shared preferences username
        prefHelper = PrefHelper(this)
        val username = prefHelper.getString(Constant.PREF_USERNAME)
        println("Username: $username")
        var textViewUsername = findViewById<TextView>(R.id.textViewUsername)
        textViewUsername.text = "Iniciado como \n \t $username"
        //crear variable para gestionar en kt el RV
        val recyclerView:RecyclerView=findViewById(R.id.rv)
        //RV : asignarle un adapter y layout
        recyclerView.layoutManager=LinearLayoutManager(this)
        var dataBase:DbHelper= DbHelper(this)
        //var clientes=ArrayList<Incidencias>()
        //clientes=dataBase.viewCliente()//devuelve un AL
        //recyclerView.adapter = Adaptador(this,clientes)
        //dataBase llamar a la función select de SQLite
        //val dataBase:DbHelper=DbHelper(this)//crea un objeto Database y la base de datos se crea
        //println(clientes)
        print("main activity arrancado y clientes consultados")
    //PENDIENTE CREAR ADAPTER PARA CARGARLO EN RV.
        //RESULTADO DEL SELECT ESTÁ EN clientes

        val botonAdd:Button=findViewById(R.id.btn_add)//activity_main.xml
        val botonView:Button=findViewById(R.id.btn_view)
        //val botonEdit:Button=findViewById(R.id.btn_edit)
        botonAdd.setOnClickListener(){
            //addItem()
            //cambiar fragment
            println("BlankFragment")
            cambiar(BlankFragment())
        }
        botonView.setOnClickListener(){
            //viewItem()
            //cambiar fragment
            println("BlankFragment2")
            cambiar(BlankFragment2())
        }
        val botonLogout:Button=findViewById(R.id.btn_logout)
        botonLogout.setOnClickListener(){
            logout()
        }
        /*
        botonEdit.setOnClickListener(){
            println("BlankFragment2")
            cambiar(BlankFragment2())
        }*/

        //error. Llamar a un id visual que NO está en tu layout
        //mi layout es activity_main y el button está en el layout add_layout


    }//cierra onCreate
    fun addItem(){
    //dataBase llamar función insertar de SQLite
        println("botón añadir pulsado")
        Toast.makeText(this@MainActivity,"añadiendo item",Toast.LENGTH_SHORT).show()
        val inflater=LayoutInflater.from(this)
        val subView=inflater.inflate(R.layout.add_layout,null)
        val builder=AlertDialog.Builder(this)
        builder.setView(subView)
        builder.create()
        builder.show()

        //codigo para gestionar elemntos de add_layout
        var layout=LayoutInflater.from(this).inflate(R.layout.add_layout,null)
        var texto = layout.findViewById<TextView>(R.id.textView)
        println("el texto es " + texto.text)
        val botonInsertar:Button=layout.findViewById(R.id.button)
        println("el boton es " + botonInsertar.text)
        //add_layout.xml
        val cajaCodigo:EditText=layout.findViewById(R.id.et_codigo)
        val cajaNombre:EditText=layout.findViewById(R.id.et_nombre)
        val cajaFacturacion:EditText=layout.findViewById(R.id.et_facturacion)

        var codigo:String=cajaCodigo.text.toString()
        var nombre:String=cajaNombre.text.toString()
        var facturacion:String=cajaFacturacion.text.toString()

        //añadir funcion dinamicamente
        botonInsertar.setOnClickListener(){
            println("boton insertar pulsado")
            println("el codigo es " + codigo)
            println("el nombre es " + nombre)
            println("la facturacion es " + facturacion)
            //dataBase.insertarCliente(codigo,nombre,facturacion)
        }



        /*
        botonInsertar.setOnClickListener(){
            println("botón insertar pulsado")
            var nuevoCliente:Clientes=Clientes(codigo.toInt(),nombre,facturacion.toFloat())
            var dataBase:DbHelper= DbHelper(this)
            dataBase.addCliente(nuevoCliente)
            finish()
            startActivity(intent)
            println("cliente insertado")
            Toast.makeText(this@MainActivity,"clientee añadido",Toast.LENGTH_SHORT).show()
        }*/

        //fin gestionar add_layout
    }
    fun cambiar(fragmento: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView2,fragmento).commit()
    }
    fun mensaje(mensaje:String){
        Toast.makeText(this@MainActivity,mensaje,Toast.LENGTH_SHORT).show()
    }
    private fun logout(){
        prefHelper.put( Constant.PREF_IS_LOGIN, false)
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

}//cierra class