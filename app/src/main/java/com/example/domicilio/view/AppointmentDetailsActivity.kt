package com.example.domicilio.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.domicilio.R

class AppointmentDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment_details)

        supportActionBar?.title = "Detalhes da consulta"
    }
}