package com.example.domicilio.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.domicilio.R
import com.example.domicilio.control.Ctl_User
import com.example.domicilio.control.UserRepository
import com.example.domicilio.services.listener.APIListener
import com.example.domicilio.services.listener.APIListenerUser
import com.example.domicilio.services.listener.ValidationListener
import com.example.domicilio.services.model.LoginModel
import com.example.domicilio.services.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class ActivityLogin : AppCompatActivity(), View.OnClickListener {

    private var mCtl_User: Ctl_User = Ctl_User()
    private var mUserRepository: UserRepository = UserRepository()

    //Chat
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        // Inicializa eventos
        observe()
        setListeners()
        firebaseAuth = FirebaseAuth.getInstance()
    }

    private fun setListeners(){
        buttonLogin.setOnClickListener(this)
        cadastreSe.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if(v.id == R.id.buttonLogin){
            mUserRepository.login(textUser.text.toString(),textSenha.text.toString(), object : APIListener{
                override fun onSuccess(model: LoginModel) {
                    TODO("Not yet implemented")
                }

                override fun onFailure(str: String) {
                    TODO("Not yet implemented")
                }

            })
/*
            var user: String =  textUser.text.toString()
            var pass: String = textSenha.text.toString()
            if(user == "" || pass == ""){
                Toast.makeText(this, "Nome do Usuário e Senha devem ser preenchidos", Toast.LENGTH_LONG).show()
            }else {
                mCtl_User.doLogin(user,pass)
            }
*/
            //signIn(textUser.text.toString(), textSenha.text.toString())
            //firebaseSignIn(textEmailFirebase.text.toString(), textSenha.text.toString())
            startActivity(Intent(this, MainActivity::class.java))
        }else if(v.id == R.id.cadastreSe){
            startActivity(Intent(this, RegisterUser::class.java))
        }
    }
    private fun signIn(user: String, pass: String){
        mCtl_User.doLogin(user,pass)
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