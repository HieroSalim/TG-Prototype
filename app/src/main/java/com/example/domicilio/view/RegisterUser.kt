package com.example.domicilio.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.domicilio.R
import com.example.domicilio.control.UserRepository
import com.example.domicilio.services.listener.APIListenerUser
import com.example.domicilio.services.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register_user.*
import java.util.*

class RegisterUser : AppCompatActivity(), View.OnClickListener {

    private val mUserRepository = UserRepository()

    //Chat
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        //Chat
        firebaseAuth = FirebaseAuth.getInstance()

        if(supportActionBar !=null){
            supportActionBar!!.hide()
        }

        //Inicializa os Eventos
        setListeners()
    }
    private fun setListeners(){
        buttonRegister.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if(v.id == R.id.buttonRegister){
            var type: String = if (checkType.isChecked) "Médico" else "User"
            var CPF: String= textCPF.text.toString()
            CPF = CPF.replace(".", "")
            CPF = CPF.replace("-", "")
            var name: String = textName.text.toString()
            var email: String = textEmail.text.toString()
            var user: String = textUsername.text.toString()
            var pass: String = textPass.text.toString()
            var cell: String = textCell.text.toString()
            if(CPF == "" || name == "" || email == "" || user == "" || pass== "" || cell == ""){
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_LONG).show()
            }
            if(verifyCPF(CPF) == false){
                Toast.makeText(this, "CPF inválido.", Toast.LENGTH_LONG).show()
            }
            else{
                mUserRepository.add(CPF,name,email,user,pass, cell,type, object : APIListenerUser{
                    override fun onSuccess(model: UserModel) {
                        firebaseSignUp(user,email,pass)
                    }

                    override fun onFailure(str: String) {
                        Toast.makeText(this@RegisterUser, "Erro no Cadastro", Toast.LENGTH_SHORT).show()
                    }

                })
            }
        }
    }

    fun verifyCPF(CPF: String): Boolean{
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

    fun editUser(){

    }

    fun firebaseSignUp(user: String, email: String, pass: String ){
        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) {task ->
                    if(task.isSuccessful){
                        val firebaseUser: FirebaseUser = firebaseAuth.currentUser!!
                        val userid = firebaseUser.uid

                        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
                                .child(userid)
                        val hashmap : HashMap<String, String> = HashMap<String, String> ()
                        hashmap["id"] = userid
                        hashmap["username"] = user
                        hashmap["imageURL"] = "default"

                        databaseReference.setValue(hashmap).addOnCompleteListener(this) {task ->
                            if(task.isSuccessful){
                                startActivity(Intent(this@RegisterUser, ActivityLogin::class.java))
                                finish()
                            }
                        }
                    }
                    else{
                        Toast.makeText(baseContext, "O registro falhou.",
                                Toast.LENGTH_SHORT).show()
                    }
        }
    }
}