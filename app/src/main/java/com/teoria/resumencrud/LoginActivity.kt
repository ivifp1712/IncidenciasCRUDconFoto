package com.teoria.resumencrud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.lazday.sharedpreferences.helper.Constant
import com.lazday.sharedpreferences.helper.PrefHelper

class LoginActivity : AppCompatActivity() {
    lateinit var prefHelper: PrefHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        prefHelper = PrefHelper(this)
        // añadir funcion al boton
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        buttonLogin.setOnClickListener {
            var editUsername = findViewById<EditText>(R.id.editUsername)
            var editPassword = findViewById<EditText>(R.id.editPassword)
            if (editUsername.text.isNotEmpty() && editPassword.text.isNotEmpty()) {
                if (editUsername.text.toString() == "ivi" && editPassword.text.toString() == "ivi") {
                    // guardar datos en shared preferences
                    saveSession(editUsername.text.toString(), editPassword.text.toString())
                    showMessage( "Iniciando sesión..." )
                    moveIntent()
                } else {
                    showMessage( "Usuario o contraseña incorrectos" )
                }
            }
        }
    }
    override fun onStart() {
        super.onStart()
        if (prefHelper.getBoolean( Constant.PREF_IS_LOGIN )) {
            println(prefHelper.getBoolean( Constant.PREF_IS_LOGIN ))
            moveIntent()
        }


    }

    private fun saveSession(username: String, password: String){
        prefHelper.put( Constant.PREF_USERNAME, username )
        prefHelper.put( Constant.PREF_PASSWORD, password )
        prefHelper.put( Constant.PREF_IS_LOGIN, true)
    }

    private fun moveIntent(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }



}