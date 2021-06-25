package com.marcosfigueroa.mueblesdres.repository

import com.marcosfigueroa.mueblesdres.model.Data
import com.marcosfigueroa.mueblesdres.retrofit.RetrofitInstance
import retrofit2.Response

class Repository {

    suspend fun getMuebles(): Response<Data> {
        return RetrofitInstance.api.getMuebles()
    }

    suspend fun login(usuario: String, password: String): Response<Data> {
        return RetrofitInstance.api.login(usuario, password)
    }
}