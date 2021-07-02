package com.marcosfigueroa.mueblesdres

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.marcosfigueroa.mueblesdres.model.Usuario
import com.marcosfigueroa.mueblesdres.repository.Repository
import com.marcosfigueroa.mueblesdres.utils.Alertas
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var sp: SharedPreferences
    private lateinit var viewModel: MainViewModel
    var alerta = Alertas()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // btnEntrar
        btnEntrar.setOnClickListener {
            val usuario = etUsuario.text.toString()
            val password = etPassword.text.toString()
            // Funcion validar
            validar(usuario, password)
        }
    }

    private fun validar(usuario: String, password: String) {
        if (usuario.isEmpty() || password.isEmpty()) {
            alerta.mostrarAlerta(this, "Advertencia", "Por favor llena todos los campos.")
        } else {
            // Mostrar progress
                mostrarProgress()
            //
            val repository = Repository()
            val viewModelFactory = MainViewModelFactory(repository)
            viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
            viewModel.login(usuario, password)
            viewModel.myResponse.observe(this, Observer { response ->
                if (response.isSuccessful) {
                    val success = response.body()?.success
                    if (success!!) {
                        // sesion
                        sp = getSharedPreferences("sesion", Context.MODE_PRIVATE)
                        var editor = sp.edit()
                        editor.putString("sesion", "1")
                        editor.apply()

                        // usuario
                        val listaUsuarios = response.body()?.data
                        val user: ArrayList<Usuario> = listaUsuarios!!
                        for (i in user) {
                            sp = getSharedPreferences("usuario", Context.MODE_PRIVATE)
                            var editor = sp.edit()
                            editor.putString("nombre", i.nombre)
                            editor.apply()
                        }

                        // activity
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        // Ocultar progress
                            ocultarProgress()
                        // Success false
                        val msg = response.body()?.msg
                        alerta.mostrarAlerta(this, "Error", msg!!)
                    }
                } else {
                    // Ocultar progress
                    ocultarProgress()
                    // Response Error
                    alerta.mostrarAlerta(this, "Error", "Problema de conexion, intenta nuevamente.")
                }
            })
        }
    }

    private fun mostrarProgress() {
        progressBar.visibility = View.VISIBLE
    }

    private fun ocultarProgress() {
        progressBar.visibility = View.GONE
    }
}