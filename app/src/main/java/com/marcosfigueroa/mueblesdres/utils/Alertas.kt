package com.marcosfigueroa.mueblesdres.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog

class Alertas {

    fun mostrarAlerta(context: Context, titulo: String, mensaje: String) {
        val dialog = AlertDialog.Builder(context)
            .setTitle(titulo)
            .setMessage(mensaje)
            .setPositiveButton("Aceptar") { view, _ ->
                view.dismiss()
            }
            .setCancelable(false)
            .create()

        dialog.show()
    }

}