package com.example.domicilio.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.domicilio.R
import kotlinx.android.synthetic.main.bar_layout.*

class ActivityProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        supportActionBar?.title = "Perfil"
        supportActionBar?.displayOptions
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    fun edit(){

    }
    fun save(){

    }
    fun addPicture(){

    }
    fun attEvalue(){

    }
}