package com.marcosfigueroa.mueblesdres

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcosfigueroa.mueblesdres.model.Data
import com.marcosfigueroa.mueblesdres.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {

    val myResponse: MutableLiveData<Response<Data>> = MutableLiveData()

    fun getMuebles() {
        viewModelScope.launch {
            val response = repository.getMuebles()
            myResponse.value = response
        }
    }

    fun login(usuario: String, password: String) {
        viewModelScope.launch {
            val response = repository.login(usuario, password)
            myResponse.value = response
        }
    }
}