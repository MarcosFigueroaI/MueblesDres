package com.marcosfigueroa.mueblesdres

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.marcosfigueroa.mueblesdres.repository.Repository
import com.marcosfigueroa.mueblesdres.utils.Alertas
import kotlinx.android.synthetic.main.activity_agregar_mueble.*

class AgregarMuebleActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    var alerta = Alertas()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_mueble)

        // Hide action bar
        supportActionBar?.hide()

        // Boton regresar
        btnRegresar.setOnClickListener {
            finish()
        }

        // Boton guardar
        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString()
            val color = etColor.text.toString()
            val precio = etPrecio.text
            val descripcion = etDescripcion.text.toString()
            val cantidad = etCantidad.text
            // Funcion validar
            validarDatos(nombre, color, precio, descripcion, cantidad)
        }
    }

    private fun validarDatos(nombre: String, color: String, precio: Editable, descripcion: String, cantidad: Editable) {
        if (nombre.isEmpty() && color.isEmpty() && precio.isEmpty() && descripcion.isEmpty() && cantidad.isEmpty()) {
            alerta.mostrarAlerta(this, "Advertencia", "Por favor llena todos los campos.")
        } else {
            println("Exito")
            val repository = Repository()
            val viewModelFactory = MainViewModelFactory(repository)
            viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
            viewModel.addMueble(nombre, color, precio.toString().toInt(), descripcion, cantidad.toString().toInt())
            viewModel.myResponse.observe(this, Observer { response ->
                if (response.isSuccessful) {
                    val success = response.body()?.success
                    if (success!!) {
                        val msg = response.body()?.msg
                        alerta.mostrarAlerta(this, "Exito", msg!!)
                        // Funcion limpiar campos
                        limpiarCampos()
                    } else {
                        // Success false
                        val msg = response.body()?.msg
                        alerta.mostrarAlerta(this, "Error", msg!!)}
                } else {
                    // Response Error
                    alerta.mostrarAlerta(this, "Error", "Problema de conexion, intenta nuevamente.")
                }
            })
        }
    }

    private fun limpiarCampos() {
        etNombre.setText("")
        etColor.setText("")
        etPrecio.setText("")
        etDescripcion.setText("")
        etCantidad.setText("")
    }
}