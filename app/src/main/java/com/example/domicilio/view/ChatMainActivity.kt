package com.example.domicilio.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domicilio.R
import com.example.domicilio.control.UserRepository
import com.example.domicilio.services.listener.APIListener
import com.example.domicilio.view.adapters.UserChat_Adapter
import com.example.domicilio.services.model.ChatListModel
import com.example.domicilio.services.model.ObjectModel
import com.example.domicilio.services.model.UserChatModel
import com.example.domicilio.services.model.UserModel
import com.example.domicilio.services.repository.local.SecurityPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.gson.internal.LinkedTreeMap
import kotlinx.android.synthetic.main.activity_chat_main.*

class ChatMainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var recyclerView: RecyclerView
    lateinit var userChatAdapter: UserChat_Adapter
    var mUsers: MutableList<UserChatModel> = mutableListOf()
    lateinit var usersList: MutableList<ChatListModel>
    private val mUserRepository = UserRepository()
    var mContext : Context = this
    lateinit var mSecurityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "Conversas"

        setContentView(R.layout.activity_chat_main)
        var mFirebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        var mDatabaseReference: DatabaseReference? =
            mFirebaseUser?.let {
                FirebaseDatabase.getInstance().getReference("Users").child(
                    it.uid
                )
            }

        mSecurityPreferences = SecurityPreferences(mContext)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(mContext)

        var fuser: FirebaseUser? = FirebaseAuth.getInstance().currentUser

        usersList = mutableListOf()

        readUsers()
    }

    override fun onClick(v: View) {

    }

    private fun readUsers() {
        val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference()

        mUserRepository.loadChats(mSecurityPreferences.get("token"), mSecurityPreferences.get("user"),
            object : APIListener<ObjectModel>{
                override fun onSuccess(result: ObjectModel) {
                    val list = result.dados as ArrayList<LinkedTreeMap<String, UserModel>>
                    list.forEach {
                        var user: String
                        if(mSecurityPreferences.get("user").equals(it.get("userClient").toString())) {
                            user = it.get("userDoctor").toString()
                        }else{
                            user = it.get("userClient").toString()
                        }
                        var query = reference.child("Users").orderByChild("username").equalTo(user)
                        query.addValueEventListener(object : ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if(snapshot.exists()){
                                    for(snapshot in snapshot.children){
                                        val user: UserChatModel? = snapshot.getValue(UserChatModel::class.java)
                                        mUsers.add(user!!)
                                    }
                                }
                                userChatAdapter =  UserChat_Adapter(mContext, mUsers, true)
                                recyclerView.adapter= userChatAdapter
                            }

                            override fun onCancelled(error: DatabaseError) {

                            }

                        })
                    }
                }

                override fun onFailure(str: String) {
                    Toast.makeText(this@ChatMainActivity, str, Toast.LENGTH_SHORT).show()
                }

            })
    }
}