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
import com.example.domicilio.services.model.UserModel
import com.example.domicilio.services.repository.local.SecurityPreferences
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register_user.*
import java.util.*

class RegisterUser : AppCompatActivity(), View.OnClickListener {

    private val mUserRepository = UserRepository()
    private lateinit var mSecurityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        supportActionBar?.title = "Registre-se"
        mSecurityPreferences = SecurityPreferences(this)

        //Inicializa os Eventos
        setListeners()
    }

    private fun setListeners() {
        buttonRegister.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.buttonRegister) {
            val type = "User"
            var CPF: String = textCPF.text.toString()
            CPF = CPF.replace(".", "")
            CPF = CPF.replace("-", "")
            val name: String = textName.text.toString()
            val email: String = textEmail.text.toString()
            val user: String = textUsername.text.toString()
            val pass: String = textPass.text.toString()
            val cell: String = textCell.text.toString()
            if (CPF == "" || name == "" || email == "" || user == "" || pass == "" || cell == "") {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_LONG)
                    .show()
            }
            if (!verifyCPF(CPF)) {
                Toast.makeText(this, "CPF inválido.", Toast.LENGTH_LONG).show()
            } else {
                mUserRepository.add(
                    baseContext,
                    this,
                    CPF,
                    name,
                    email,
                    user,
                    pass,
                    cell,
                    type,
                    object : APIListener<UserModel> {
                        override fun onSuccess(model: UserModel) {
                            var javaClass = if (checkType.isChecked) RegisterDoctor::class.java
                            else MainActivity::class.java
                            signIn(email,pass)
                            if (checkType.isChecked) mSecurityPreferences.store("CPF", CPF)

                            startActivity(Intent(this@RegisterUser, javaClass))
                            finish()
                        }

                        override fun onFailure(str: String) {
                            Toast.makeText(
                                this@RegisterUser,
                                "Erro no Cadastro",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
            }
        }
    }

    private fun verifyCPF(CPF: String): Boolean {
        if (CPF == "00000000000" || CPF == "11111111111" || CPF == "22222222222" || CPF == "33333333333" || CPF == "44444444444" || CPF == "55555555555" || CPF == "66666666666" || CPF == "77777777777" || CPF == "88888888888" || CPF == "99999999999" ||
            CPF.length != 11
        ) return false
        val dig10: Char
        val dig11: Char
        var sm: Int
        var i: Int
        var r: Int
        var num: Int
        var peso: Int

        return try {
            sm = 0
            peso = 10
            i = 0
            while (i < 9) {
                num = (CPF[i].toInt() - 48)
                sm = sm + num * peso
                peso = peso - 1
                i++
            }
            r = 11 - sm % 11
            dig10 =
                if (r == 10 || r == 11) '0' else (r + 48).toChar()
            sm = 0
            peso = 11
            i = 0
            while (i < 10) {
                num = (CPF[i].toInt() - 48)
                sm = sm + num * peso
                peso = peso - 1
                i++
            }
            r = 11 - sm % 11
            dig11 = if (r == 10 || r == 11) '0' else (r + 48).toChar()


            if (dig10 == CPF[9] && dig11 == CPF[10]) true else false
        } catch (erro: InputMismatchException) {
            false
        }
    }

    fun editUser() {

    }

    private fun signIn(email: String,pass: String){
        mUserRepository.login(baseContext, this, email, pass, object : APIListener<LoginModel> {
            override fun onSuccess(model: LoginModel) {
                mSecurityPreferences.store("token", model.token)
                mSecurityPreferences.store("auth", model.auth.toString())
            }

            override fun onFailure(str: String) {
                Toast.makeText(this@RegisterUser, str, Toast.LENGTH_SHORT).show()
            }
        })
    }
}