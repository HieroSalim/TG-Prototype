package com.example.domicilio.view

import TermsDialog
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class ActivityLogin : AppCompatActivity(), View.OnClickListener {

    private val mUserRepository: UserRepository = UserRepository()
    private lateinit var mSecurityPreferences: SecurityPreferences


    override fun onStart() {
        //Mantendo sessão no aplicativo
        super.onStart()
        var firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser

        if(mSecurityPreferences.get("token") !=  ""){
            if(firebaseUser != null){
                startActivity(Intent(this, MainActivity::class.java))
                finish();
            }
            else{
                Toast.makeText(this, "Usuário não está logado no firebase", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        // Inicializa eventos
        setListeners()
        mSecurityPreferences = SecurityPreferences(this)
    }

    private fun setListeners(){
        buttonLogin.setOnClickListener(this)
        cadastreSe.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if(v.id == R.id.buttonLogin){
            signIn()
        }else if(v.id == R.id.cadastreSe){
            startActivity(Intent(this, RegisterUser::class.java))
        }
    }
    private fun signIn(){
        val login: String =  textUser.text.toString()
        val pass: String = textSenha.text.toString()
        if(login == "" || pass == ""){
            Toast.makeText(this, "Nome do Usuário e Senha devem ser preenchidos", Toast.LENGTH_LONG).show()
        }else {
            mUserRepository.login(baseContext, this, login, pass, object : APIListener<LoginModel>{
                override fun onSuccess(model: LoginModel) {
                    mSecurityPreferences.store("token", model.token)
                    mSecurityPreferences.store("auth", model.auth.toString())
                }

                override fun onFailure(str: String) {
                    Toast.makeText(this@ActivityLogin, str, Toast.LENGTH_SHORT).show()
                }

            })
        }
    }
}