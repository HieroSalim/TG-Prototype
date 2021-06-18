package com.example.domicilio.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domicilio.R
import com.example.domicilio.control.AppointmentRepository
import com.example.domicilio.services.listener.APIListener
import com.example.domicilio.services.model.AppointmentModel
import com.example.domicilio.view.adapters.UserChat_Adapter
import com.example.domicilio.services.model.ChatListModel
import com.example.domicilio.services.model.ObjectModel
import com.example.domicilio.services.model.UserChatModel
import com.example.domicilio.services.repository.local.SecurityPreferences
import com.example.domicilio.view.adapters.Appointment_Adapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat_main.*

class ChatMainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var recyclerView: RecyclerView
    lateinit var userChatAdapter: UserChat_Adapter
    var mUsers: MutableList<UserChatModel> = mutableListOf()
    lateinit var usersList: MutableList<ChatListModel>
    var mContext : Context = this
    private val mAppointmentRepository = AppointmentRepository()
    lateinit var mSecurityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "Conversas"
        mSecurityPreferences = SecurityPreferences(this)

        setContentView(R.layout.activity_chat_main)
        var mFirebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        var mDatabaseReference: DatabaseReference? =
            mFirebaseUser?.let {
                FirebaseDatabase.getInstance().getReference("Users").child(
                    it.uid
                )
            }

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
        val firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        var reference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
        var query: Query = reference.child("Users").orderByChild("username").equalTo("")
        query.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                mUsers.clear()
                for (snapshot in snapshot.children){
                    val user: UserChatModel? = snapshot.getValue(UserChatModel::class.java)
                    var i: Int = 0
                    //if(appointments.size>0){
                        //while(i <= appointments.size) {
                            //if (user!!.username.equals(appointments[i].doctors)){
                                //mUsers.add(user)
                           // }
                            //i++
                        //}
                   // }
                    //else{
                        //Toast.makeText( this@ChatMainActivity,"Não há nenhuma conversa para visualizar.", Toast.LENGTH_LONG).show()
                    //}
                }
                userChatAdapter =  UserChat_Adapter(mContext, mUsers, true)
                recyclerView.adapter= userChatAdapter
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}