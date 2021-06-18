package com.example.domicilio.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domicilio.R
import com.example.domicilio.view.adapters.Message_Adapter
import com.example.domicilio.services.model.ChatModel
import com.example.domicilio.services.model.UserChatModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MessageActivity : AppCompatActivity() {
    lateinit var username: TextView
    lateinit var fuser: FirebaseUser
    lateinit var reference: DatabaseReference
    lateinit var button_send: ImageButton

    lateinit var messageAdapter: Message_Adapter
    lateinit var mchat: MutableList<ChatModel>

    lateinit var recyclerView: RecyclerView

    lateinit var seenListener: ValueEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        supportActionBar?.title = "Conversas"

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(applicationContext)
        linearLayoutManager.stackFromEnd
        recyclerView.layoutManager = linearLayoutManager


        username = findViewById(R.id.username)
        button_send = findViewById(R.id.button_send)


        intent = intent
        val userid: String = intent.getStringExtra("userid")!!

        button_send.setOnClickListener {
            @Override
            fun onClick(v: View) {
                val msg: String = text_send.text.toString()
                val id = v.id
                if (id == R.id.button_send) {
                    if (!msg.equals("")) {
                        sendMessage(fuser.uid, userid, msg)
                    } else {
                        Toast.makeText(
                            baseContext, "Você não pode enviar uma mensagem vazia.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                text_send.setText("")
            }
            onClick(button_send)
        }

        fuser = FirebaseAuth.getInstance().currentUser!!
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userid)

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userChatModel: UserChatModel? = snapshot.getValue(UserChatModel::class.java)
                username.text = userChatModel?.username
                readMessage(fuser.uid, userid, userChatModel!!.imageURL)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        seenMessage(userid)
    }

    private fun seenMessage(userid: String){
        reference = FirebaseDatabase.getInstance().getReference("Chats")
        seenListener = reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(snapshot in snapshot.children){
                    var chatModel: ChatModel? = snapshot.getValue(ChatModel::class.java)
                    if(chatModel?.receiver.equals(fuser.uid) && chatModel?.sender.equals(userid)){
                        var hashMap: HashMap<String, Any> = HashMap()
                        hashMap.put("isseen", true)
                        snapshot.ref.updateChildren(hashMap)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun sendMessage(sender: String, receiver: String, message: String){
        reference = FirebaseDatabase.getInstance().getReference()
        var userid: String? = intent.getStringExtra("userid")

        var hashmap : HashMap<String, Any> = HashMap()
        hashmap.put("sender", sender)
        hashmap.put("receiver", receiver)
        hashmap.put("message", message)
        hashmap.put("isseen", false)

        reference.child("Chats").push().setValue(hashmap)

        val chatRefReceiver: DatabaseReference = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(fuser.uid)
                .child(userid!!)
        val chatRefSender: DatabaseReference = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(userid!!)
                .child(fuser.uid)

        chatRefReceiver.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(!snapshot.exists()){
                    chatRefReceiver.child("id").setValue(userid)
                    chatRefSender.child("id").setValue(fuser.uid)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun readMessage(myid: String, userid: String, imageurl: String){
        mchat = mutableListOf()

        reference = FirebaseDatabase.getInstance().getReference("Chats")
        reference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mchat.clear()
                for (snapshot in snapshot.children){
                    var chatModel: ChatModel? = snapshot.getValue(ChatModel::class.java)
                    if(chatModel?.receiver.equals(myid) && chatModel?.sender.equals(userid)||
                        chatModel?.receiver.equals(userid) && chatModel?.sender.equals(myid)){
                        chatModel?.let { mchat.add(it) }
                    }
                    var ctl_Message = Message_Adapter(imageurl, this@MessageActivity, mchat)
                    recyclerView.adapter = ctl_Message
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        } )
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        reference.removeEventListener(seenListener)
    }
}