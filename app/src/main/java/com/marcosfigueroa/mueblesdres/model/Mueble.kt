package com.marcosfigueroa.mueblesdres.model

import java.io.Serializable

data class Mueble(
    val nombre: String,
    val color: String,
    val precio: String,
    val descripcion: String,
    val cantidad: String
): Serializable