package com.example.domicilio.control

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.domicilio.services.listener.APIListener
import com.example.domicilio.services.model.LoginModel
import com.example.domicilio.services.model.MessageModel
import com.example.domicilio.services.model.ObjectModel
import com.example.domicilio.services.model.UserModel
import com.example.domicilio.services.repository.remote.RetrofitClient
import com.example.domicilio.services.repository.remote.UserService
import com.example.domicilio.view.MainActivity
import com.example.domicilio.view.RegisterDoctor
import com.example.domicilio.view.RegisterUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap


class UserRepository {

    private val mRemote = RetrofitClient.createService(UserService::class.java)

    //Chat
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    fun login(context: Context, activity: Activity, login: String, pass: String, listener: APIListener<LoginModel>) {
        val call: Call<LoginModel> = mRemote.login(login, pass)
        //Assíncrona
        call.enqueue(object : Callback<LoginModel> {
            override fun onResponse(call: Call<LoginModel>, response: Response<LoginModel>) {
                if (response.code() != 202) {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    listener.onFailure(jObjError.getString("mensagem"))
                } else {
                    firebaseAuth = FirebaseAuth.getInstance()
                    this@UserRepository.firebaseSignIn(context, activity, login, pass)
                    response.body()?.let { listener.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<LoginModel>, t: Throwable) {
                listener.onFailure("Ocorreu um erro inesperado. Tente novamente mais tarde.")
            }

        })
    }

    fun add(context: Context, activity: Activity,CPF: String,name: String, email:String, user: String, pass: String, cell: String, typeUser: String, listener: APIListener<UserModel>) {
        val call: Call<UserModel> = mRemote.register(CPF, name, email, user, pass, cell, typeUser)
        //Assíncrona
        call.enqueue(object : Callback<UserModel> {
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                if (response.code() != 201) {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    listener.onFailure(jObjError.getString("mensagem"))
                } else {
                    firebaseAuth = FirebaseAuth.getInstance()
                    this@UserRepository.firebaseSignUp(context, activity, user, email, pass)
                    response.body()?.let { listener.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                listener.onFailure("Ocorreu um erro inesperado. Tente novamente mais tarde.")
            }
        })
    }

    fun loadSession(token: String, listener: APIListener<UserModel>){
        val call: Call<UserModel> = mRemote.loadSession("Bearer $token")
        //Assíncrona
        call.enqueue(object : Callback<UserModel> {
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                if(response.code() != 200) {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    if(response.code() == 400 || response.code() == 401){
                        listener.onFailure(jObjError.getString("mensagem"))
                    }else{
                        listener.onFailure(jObjError.getString("error"))
                    }
                }
                else {
                    response.body()?.let { listener.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                listener.onFailure("Ocorreu um erro inesperado. Tente novamente mais tarde.")
            }
        })
    }

    fun firebaseSignUp(context: Context, activity: Activity,user: String, email: String, pass: String ){
        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(activity) {task ->
            if(task.isSuccessful){
                val firebaseUser: FirebaseUser = firebaseAuth.currentUser!!
                val userid = firebaseUser.uid

                databaseReference = FirebaseDatabase.getInstance().getReference("Users")
                    .child(userid)
                val hashmap : HashMap<String, String> = HashMap<String, String> ()
                hashmap["id"] = userid
                hashmap["username"] = user
                hashmap["imageURL"] = "default"

                databaseReference.setValue(hashmap).addOnCompleteListener(activity) {task ->
                    if(task.isSuccessful){
                    }
                }
            }
            else{
                Toast.makeText(context, "O registro no firebase falhou.",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseSignIn(context: Context, activity: Activity, email: String, pass: String){
        firebaseAuth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(activity) {task ->
                if(task.isSuccessful){
                    lateinit var sharingIntent : Intent
                    val javaClass = if (activity == RegisterUser::class.java) RegisterDoctor::class.java
                    else MainActivity::class.java
                    context.startActivity(Intent(activity, javaClass).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                    activity.finish()
                }
                else{
                    Toast.makeText(context, "A autenticação falhou.",
                        Toast.LENGTH_SHORT).show()
                }
        }
    }

    fun acceptTerm(context: Context, token: String,user: String, status: Int){
        val call: Call<LoginModel> = mRemote.acceptTerm(token ,user, status)

        call.enqueue(object : Callback<LoginModel> {
            override fun onResponse(call: Call<LoginModel>, response: Response<LoginModel>) {
                if(response.code() != 200) {
                    Toast.makeText(context, "Ocorreu um erro inesperado. Tente novamente mais tarde.", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<LoginModel>, t: Throwable) {
                Toast.makeText(context, "Ocorreu um erro inesperado. Tente novamente mais tarde.", Toast.LENGTH_LONG).show()
            }

        })
    }

    fun loadData(token: String, user: String, listener: APIListener<UserModel>){
        val call: Call<UserModel> = mRemote.loadData(token, user)

        call.enqueue(object : Callback<UserModel>{
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                if(response.code() != 200){
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    listener.onFailure(jObjError.getString("mensagem"))
                }else{
                    response.body()?.let { listener.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                listener.onFailure("Ocorreu um erro inesperado. Tente novamente mais tarde.")
            }

        })
    }

    fun editData(token: String, user: String, name: String, cell: String, listener: APIListener<MessageModel>){
        val call: Call<MessageModel> = mRemote.editData("Bearer $token", user, name, cell)

        call.enqueue(object : Callback<MessageModel>{
            override fun onResponse(call: Call<MessageModel>, response: Response<MessageModel>) {
                if(response.code() != 200){
                    listener.onFailure("Ocorreu um erro inesperado. Tente novamente mais tarde.")
                }else{
                    response.body()?.let { listener.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<MessageModel>, t: Throwable) {
                listener.onFailure("Ocorreu um erro inesperado. Tente novamente mais tarde.")
            }

        })
    }

    fun loadChats(token: String, user: String,listener: APIListener<ObjectModel>){
        val call: Call<ObjectModel> = mRemote.loadChats("Bearer $token", user)

        call.enqueue(object : Callback<ObjectModel>{
            override fun onResponse(call: Call<ObjectModel>, response: Response<ObjectModel>) {
                if(response.code() != 200){
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    listener.onFailure(jObjError.getString("mensagem"))
                }else{
                    response.body()?.let { listener.onSuccess(it) }
                }
            }

            override fun onFailure(call: Call<ObjectModel>, t: Throwable) {
                listener.onFailure("Ocorreu um erro inesperado. Tente novamente mais tarde.")
            }

        })
    }
}