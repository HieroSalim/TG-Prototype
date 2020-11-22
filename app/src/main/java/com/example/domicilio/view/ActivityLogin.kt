package com.example.domicilio.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.domicilio.R
import com.example.domicilio.control.Ctl_User
import com.example.domicilio.services.model.Login
import com.example.domicilio.services.model.LoginModel
import kotlinx.android.synthetic.main.activity_login.*

class ActivityLogin : AppCompatActivity(), View.OnClickListener {

    private var mCtl_User: Ctl_User = Ctl_User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicializa eventos
        setListeners()
    }

    private fun setListeners(){
        buttonLogin.setOnClickListener(this)
        cadastreSe.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if(v.id == R.id.buttonLogin){
            signIn(textUser.toString(), textSenha.toString())
        }else if(v.id == R.id.cadastreSe){
            startActivity(Intent(this, RegisterUser::class.java))
        }
    }

    private fun signIn(user: String, pass: String){
        mCtl_User.filter(user, pass)
    }

    private fun newAccount(){

    }
    private fun verifyData(){

    }
}