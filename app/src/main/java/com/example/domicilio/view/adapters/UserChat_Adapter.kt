package com.example.domicilio.view.adapters
import com.example.domicilio.R
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domicilio.services.model.ChatModel
import com.example.domicilio.services.model.UserChatModel
import com.example.domicilio.view.MessageActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class UserChat_Adapter: RecyclerView.Adapter<UserChat_Adapter.ViewHolder>{
    var mContext: Context
    var mUsers: MutableList<UserChatModel>
    var ischat: Boolean

    constructor(mContext: Context, mUsers: MutableList<UserChatModel>, ischat: Boolean) {
        this.mContext = mContext
        this.mUsers = mUsers
        this.ischat = ischat
    }

    class ViewHolder: RecyclerView.ViewHolder {
        constructor(itemView: View) : super(itemView) {
            this.username = username
            this.profile_img = profile_img
            this.last_msg = last_msg
        }
        var username: TextView = itemView.findViewById(R.id.username)
        var profile_img: ImageView = itemView.findViewById(R.id.profile_image)
        var last_msg: TextView = itemView.findViewById(R.id.last_msg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user: UserChatModel = mUsers[position]
        holder.username.text = user.username
        if(user.imageURL == "default"){
            holder.profile_img.setImageResource(R.mipmap.ic_launcher)
        } else{
            Glide.with(mContext).load(user.imageURL).into(holder.profile_img)
        }

        if(ischat){
            lastMessage(user.id, holder.last_msg)
        }else{
            holder.last_msg.visibility = View.GONE
        }

        holder.itemView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val intent: Intent = Intent(mContext, MessageActivity::class.java)
                intent.putExtra("userid", user.id)
                mContext.startActivity(intent)
            }
        })
    }

    override fun getItemCount(): Int {
        return mUsers.size
    }

    fun lastMessage(userid: String, last_msg: TextView){
        var theLastMessage: String = "Default"
        val firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Chats")

        reference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(snapshot in snapshot.children){
                    val chat: ChatModel? = snapshot.getValue(ChatModel::class.java)
                    if(chat?.receiver.equals(firebaseUser?.uid) && chat?.sender.equals(userid)||
                        chat?.receiver.equals(userid) && chat?.sender.equals(firebaseUser?.uid)){
                        theLastMessage = chat!!.message
                    }
                }
                if(theLastMessage == "default"){
                    last_msg.text = "No Message"
                }
                else{
                    last_msg.setText(theLastMessage)
                }
                theLastMessage = "default"
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}