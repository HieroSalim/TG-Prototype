package com.example.domicilio.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.domicilio.R
import com.example.domicilio.control.UserRepository
import com.example.domicilio.services.listener.APIListener
import com.example.domicilio.services.model.LoginModel
import com.example.domicilio.services.repository.local.SecurityPreferences
import kotlinx.android.synthetic.main.activity_login.*

class ActivityLogin : AppCompatActivity(), View.OnClickListener {

    private val mUserRepository: UserRepository = UserRepository()
    private lateinit var mSecurityPreferences: SecurityPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        // Inicializa eventos
        setListeners()
        mSecurityPreferences = SecurityPreferences(this)
        verifyData()
    }

    private fun setListeners(){
        buttonLogin.setOnClickListener(this)
        cadastreSe.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if(v.id == R.id.buttonLogin){
            signIn()
        }else if(v.id == R.id.cadastreSe){
            startActivity(Intent(this, RegisterDoctor::class.java))
        }
    }
    private fun signIn(){
        val login: String =  textUser.text.toString()
        val pass: String = textSenha.text.toString()
        if(login == "" || pass == ""){
            Toast.makeText(this, "Nome do Usuário e Senha devem ser preenchidos", Toast.LENGTH_LONG).show()
        }else {
            mUserRepository.login(baseContext, this, login, pass, object : APIListener{
                override fun onSuccess(model: LoginModel) {
                    mSecurityPreferences.store("token", model.token)
                }

                override fun onFailure(str: String) {
                    Toast.makeText(this@ActivityLogin, str, Toast.LENGTH_SHORT).show()
                }

            })
        }
    }

    private fun newAccount(){

    }
    private fun verifyData(){
        if(mSecurityPreferences.get("token") !=  ""){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
}