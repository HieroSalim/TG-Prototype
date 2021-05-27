package com.example.domicilio.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.example.domicilio.R
import kotlinx.android.synthetic.main.activity_register_doctor.*

class RegisterDoctor : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_doctor)

        supportActionBar?.title = "Cadastro médico"

        //Inicializa eventos
        setListeners()
        populateSpinner()
    }

    fun populateSpinner(){
        val list: MutableList<String> = arrayListOf()
        list.add("CRM")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list)
        typeProfessional.adapter = adapter
    }

    fun setListeners(){

    }

    override fun onClick(v: View) {
        TODO("Not yet implemented")
    }
}