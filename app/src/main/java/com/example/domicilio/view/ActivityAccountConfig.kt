package com.example.domicilio.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.domicilio.R
import com.example.domicilio.services.repository.local.SecurityPreferences
import kotlinx.android.synthetic.main.activity_account_config.*


class ActivityAccountConfig : AppCompatActivity(), View.OnClickListener {

    private lateinit var mSecurityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_config)
        supportActionBar?.title = "Editar informações de perfil"

        val parent = findViewById<View>(R.id.parent)
        mSecurityPreferences = SecurityPreferences(this)

        setListeners()
        loadData()
    }

    private fun loadData(){
        user_name_edit.setText(mSecurityPreferences.get("name"))
    }

    private fun setListeners(){
        update_button.setOnClickListener(this)
    }

    fun disableUser(){

    }
    fun editAccount(){

    }
    fun verifyAccount(){
    }

    override fun onClick(v: View) {

    }

}