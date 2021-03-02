package com.example.domicilio.view

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.domicilio.R
import com.example.domicilio.control.Ctl_User
import kotlinx.android.synthetic.main.activity_account_config.*
import kotlinx.android.synthetic.main.activity_account_config.view.*


class ActivityAccountConfig : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_config)


        if(supportActionBar !=null){
            supportActionBar!!.hide()
        }

        val parent = findViewById<View>(R.id.parent)

        setListeners()
        observe()

    }

    private fun setListeners(){
        update_button.setOnClickListener(this)

    }

    private fun observe(){

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