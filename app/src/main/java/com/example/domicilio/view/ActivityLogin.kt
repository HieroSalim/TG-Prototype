package com.example.domicilio.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.domicilio.R
import com.example.domicilio.control.Ctl_User
import com.example.domicilio.services.listener.APIListener
import com.example.domicilio.services.model.LoginModel
import com.example.domicilio.services.repository.UserRepository
import kotlinx.android.synthetic.main.activity_login.*

class ActivityLogin : AppCompatActivity(), View.OnClickListener {

    private var mCtl_User: Ctl_User = Ctl_User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        // Inicializa eventos
        observe()
        setListeners()
    }

    private fun setListeners(){
        buttonLogin.setOnClickListener(this)
        cadastreSe.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if(v.id == R.id.buttonLogin){
            var user: String =  textUser.text.toString()
            var pass: String = textSenha.text.toString()
            if(user == "" || pass == ""){
                Toast.makeText(this, "Nome do Usuário e Senha devem ser preenchidos", Toast.LENGTH_LONG).show()
            }else {
                mCtl_User.doLogin(user,pass)
            }
        }else if(v.id == R.id.cadastreSe){
            startActivity(Intent(this, RegisterUser::class.java))
        }
    }

    private fun observe(){
        mCtl_User.login.observe(this, Observer {
            if(it.success()){
                startActivity(Intent(this, MainActivity::class.java))
            }else{
                val message = it.failure()
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun newAccount(){

    }
    private fun verifyData(){

    }
}