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
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class ActivityLogin : AppCompatActivity(), View.OnClickListener {

    private val mUserRepository: UserRepository = UserRepository()

    //Chat
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        // Inicializa eventos
        setListeners()
        firebaseAuth = FirebaseAuth.getInstance()
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
        var login: String =  textUser.text.toString()
        var pass: String = textSenha.text.toString()
        if(login == "" || pass == ""){
            Toast.makeText(this, "Nome do Usuário e Senha devem ser preenchidos", Toast.LENGTH_LONG).show()
        }else {
            mUserRepository.login(login,pass, object : APIListener{
                override fun onSuccess(model: LoginModel) {
                    firebaseSignIn(textEmailFirebase.text.toString(), textSenha.text.toString())
                    startActivity(Intent(this@ActivityLogin, MainActivity::class.java))
                    finish()
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

    }

    private fun firebaseSignIn(email: String, pass: String){
        if(email == null || pass == null ){
            Toast.makeText(baseContext, "Todos os campos são obrigatórios",
                Toast.LENGTH_SHORT).show()
        }
        else{
            firebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this) {task ->
                    if(task.isSuccessful){
                        var intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(baseContext, "A autenticação falhou.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}