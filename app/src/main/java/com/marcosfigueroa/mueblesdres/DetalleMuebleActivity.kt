package com.marcosfigueroa.mueblesdres

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.marcosfigueroa.mueblesdres.model.Mueble
import kotlinx.android.synthetic.main.activity_detalle_mueble.*

class DetalleMuebleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_mueble)

        // Hide action bar
        supportActionBar?.hide()

        // Boton regresar
        btnRegresar.setOnClickListener {
            finish()
        }

        val mueble: Mueble = intent.getSerializableExtra("mueble") as Mueble

        detalleMuebleNombre_txt.text = mueble.nombre
        detalleMuebleCantidad_txt.text = "Cantidad: ${mueble.cantidad}"
        detalleMuebleDescripcion_txt.text = mueble.descripcion
    }
}