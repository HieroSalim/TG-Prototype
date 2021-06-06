package com.example.domicilio.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domicilio.R
import com.example.domicilio.view.adapters.UserChat_Adapter
import com.example.domicilio.services.model.ChatListModel
import com.example.domicilio.services.model.UserChatModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat_main.*

class ChatMainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var recyclerView: RecyclerView
    lateinit var userChatAdapter: UserChat_Adapter
    lateinit var mUsers: MutableList<UserChatModel>
    lateinit var reference: DatabaseReference
    lateinit var usersList: MutableList<ChatListModel>
    var mContext : Context = this

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

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(mContext)

        var fuser: FirebaseUser? = FirebaseAuth.getInstance().currentUser

        usersList = mutableListOf()

        reference =  FirebaseDatabase.getInstance().getReference("Chatlist").child(fuser!!.uid)
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                usersList.clear()
                for(snapshot in snapshot.children){
                    val chatListModel: ChatListModel? = snapshot.getValue(ChatListModel::class.java)
                    chatListModel?.let { usersList.add(it) }
                }

                chatList()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        mDatabaseReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userChatModel: UserChatModel? = snapshot.getValue(UserChatModel::class.java)
                if (userChatModel != null) {
                    user_name_main.text = userChatModel.username
                }
                if (userChatModel != null) {
                    if (userChatModel.imageURL == "default") {
                        profile_image.setImageResource(R.mipmap.ic_launcher)
                    } else {

                        Glide.with(applicationContext).load(userChatModel.imageURL).into(profile_image)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    override fun onClick(v: View) {

    }

    private fun chatList() {
        mUsers = mutableListOf()
        reference = FirebaseDatabase.getInstance().getReference("Users")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mUsers.clear()
                for(snapshot in snapshot.children){
                    val userChatModel: UserChatModel? = snapshot.getValue(UserChatModel ::class.java)

                    for(chatListModel: ChatListModel in usersList){
                        if(userChatModel!!.id == chatListModel.id){
                            mUsers.add(userChatModel)
                        }
                    }
                }
                userChatAdapter = UserChat_Adapter(mContext, mUsers, ischat = true)
                recyclerView.adapter = userChatAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}