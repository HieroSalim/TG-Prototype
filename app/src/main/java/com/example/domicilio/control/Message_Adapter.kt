package com.example.domicilio.control

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domicilio.R
import com.example.domicilio.services.model.ChatModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Message_Adapter: RecyclerView.Adapter<Message_Adapter.ViewHolder>{
    var mContext: Context
    var mChat: MutableList<ChatModel>
    private val MSG_TYPE_LEFT = 0
    private val MSG_TYPE_RIGHT = 1
    private val imageurl: String

    lateinit var fuser: FirebaseUser

    constructor(imageurl: String, mContext: Context, mChat: MutableList<ChatModel>) {
        this.mContext = mContext
        this.mChat = mChat
        this.imageurl = imageurl
    }

    class ViewHolder: RecyclerView.ViewHolder {
        constructor(itemView: View) : super(itemView) {
            this.show_message = show_message
            this.profile_img = profile_img
            this.txt_seen = txt_seen
        }
        var show_message: TextView = itemView.findViewById(R.id.show_message)
        var profile_img: ImageView = itemView.findViewById(R.id.profile_image)
        var txt_seen: TextView = itemView.findViewById(R.id.txt_seen)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType ==  MSG_TYPE_RIGHT){
            val view: View = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false)
            ViewHolder(view)
        } else{
            val view: View = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false)
            ViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chatModel: ChatModel = mChat.get(position)

        holder.show_message.setText(chatModel.message)

        if(imageurl.equals("default")){
            holder.profile_img.setImageResource(R.mipmap.ic_launcher)
        } else{
            Glide.with(mContext).load(imageurl).into(holder.profile_img)
        }

        if(position == mChat.size - 1){
            if(chatModel.isseen){
                holder.txt_seen.text ="Visto"
            }else{
                holder.txt_seen.text ="Enviada"
            }
        }else{
            holder.txt_seen.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return mChat.size
    }

    override fun getItemViewType(position: Int): Int {
        fuser = FirebaseAuth.getInstance().currentUser!!
        return if (mChat.get(position).sender.equals(fuser.uid)){
            MSG_TYPE_RIGHT
        } else{
            MSG_TYPE_LEFT
        }
    }
}