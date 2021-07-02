package com.marcosfigueroa.mueblesdres

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.marcosfigueroa.mueblesdres.adapter.MyAdapter
import com.marcosfigueroa.mueblesdres.adapter.OnItemClickListener
import com.marcosfigueroa.mueblesdres.model.Mueble
import com.marcosfigueroa.mueblesdres.repository.Repository
import com.marcosfigueroa.mueblesdres.utils.Alertas
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var sp: SharedPreferences
    private lateinit var viewModel: MainViewModel
    var adapterMuebles = MyAdapter(this)
    var listaMuebles = arrayListOf<Mueble>()
    var alerta = Alertas()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Hide action bar
        supportActionBar?.hide()

        // Boton menu
        btnMenu.setOnClickListener {
            // session
            sp = getSharedPreferences("sesion", Context.MODE_PRIVATE)
            var editor = sp.edit()
            editor.putString("sesion", "0")
            editor.apply()

            // activity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // Boton agregar mueble
        btnAgregarMueble.setOnClickListener {
            startActivity(Intent(this, AgregarMuebleActivity::class.java))
        }

        setupRecyclerview()

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getMuebles()
        viewModel.myResponse.observe(this, Observer { response ->
            if (response.isSuccessful) {
                // success true
                listaMuebles = response.body()?.listaMuebles!!
                adapterMuebles.setData(listaMuebles)
            } else {
                // error successful
                val error = response.code().toString()
                val errorBody = response.errorBody().toString()
                alerta.mostrarAlerta(this, error, errorBody)
            }
        })

    }

    private fun setupRecyclerview() {
        recyclerView.adapter = adapterMuebles
        //recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun itemClick(item: Mueble, position: Int) {
        sp = getSharedPreferences("muebles", Context.MODE_PRIVATE)
        var editor = sp.edit()
        editor.putString("nombreMueble", item.nombre)
        editor.apply()

        //intent
        val intent = Intent(this, DetalleMuebleActivity::class.java).apply {
            putExtra("mueble", item)
        }

        //startActivityIntent
        startActivity(intent)
    }
}


