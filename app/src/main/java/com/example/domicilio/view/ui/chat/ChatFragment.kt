package com.example.domicilio.view.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domicilio.R
import com.example.domicilio.control.UserChat_Adapter
import com.example.domicilio.services.model.ChatListModel
import com.example.domicilio.services.model.UserChatModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class ChatFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var userChatAdapter: UserChat_Adapter
    lateinit var mUsers: MutableList<UserChatModel>
    lateinit var reference: DatabaseReference
    lateinit var usersList: MutableList<ChatListModel>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_chat, container, false)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

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
        return view
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
                userChatAdapter = UserChat_Adapter(context!!, mUsers, ischat = true)
                recyclerView.adapter = userChatAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}