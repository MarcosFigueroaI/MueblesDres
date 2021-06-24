package com.marcosfigueroa.mueblesdres.repository

import com.marcosfigueroa.mueblesdres.model.Data
import com.marcosfigueroa.mueblesdres.retrofit.RetrofitInstance
import retrofit2.Response

class Repository {

    suspend fun getMuebles(): Response<Data> {
        return RetrofitInstance.api.getMuebles()
    }
}