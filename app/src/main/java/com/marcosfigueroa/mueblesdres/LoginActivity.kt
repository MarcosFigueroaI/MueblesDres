package com.marcosfigueroa.mueblesdres

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.marcosfigueroa.mueblesdres.model.Usuario
import com.marcosfigueroa.mueblesdres.repository.Repository
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var sp: SharedPreferences
    private lateinit var viewModel: MainViewModel

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
            mostrarAlerta("Advertencia", "Por favor llena todos los campos.")
        } else {
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
                        val msg = response.body()?.msg
                        println(msg)
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
                        // Success false
                        val msg = response.body()?.msg
                        mostrarAlerta("Error", msg!!)
                    }
                } else {
                    // Response Error
                    mostrarAlerta("Error", "Problema de conexion, intenta nuevamente.")
                }
            })
        }
    }

    private fun mostrarAlerta(titulo: String, mensaje: String) {
        val dialog = AlertDialog.Builder(this)
            .setTitle(titulo)
            .setMessage(mensaje)
            /*.setNegativeButton("Cancelar") { view, _ ->
                Toast.makeText(this, "Cancel button pressed", Toast.LENGTH_SHORT).show()
                view.dismiss()
            }*/
            .setPositiveButton("Aceptar") { view, _ ->
                //Toast.makeText(this, "Ok button pressed", Toast.LENGTH_SHORT).show()
                view.dismiss()
            }
            .setCancelable(false)
            .create()

        dialog.show()
    }
}