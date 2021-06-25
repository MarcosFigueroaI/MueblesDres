package com.marcosfigueroa.mueblesdres

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate

class LaunchActivity : AppCompatActivity() {

    private lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        // No dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // sesion
        sp = getSharedPreferences("sesion", Context.MODE_PRIVATE)
        val sesion = sp.getString("sesion", "")

        if (sesion != "1") {
            // login
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else {
            // main
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}