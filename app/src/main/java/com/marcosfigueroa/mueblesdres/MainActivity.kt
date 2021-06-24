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
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var sp: SharedPreferences
    private lateinit var viewModel: MainViewModel
    var adapterMuebles = MyAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // No dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // Hide action bar
        supportActionBar?.hide()

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
                response.body()?.let { adapterMuebles.setData(it?.listaMuebles) }
            } else {
                println(response.errorBody().toString())
                println(response.code().toString())
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


