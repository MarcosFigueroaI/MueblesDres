package com.marcosfigueroa.mueblesdres.model

data class Data(
    val listaMuebles: ArrayList<Mueble>,
    val data: ArrayList<Usuario>,
    val success: Boolean,
    val msg: String
)