package com.example.domicilio.view.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.domicilio.R

class ChatFragment : Fragment() {

    private lateinit var chatViewModel: ChatViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        chatViewModel =
                ViewModelProvider(this).get(ChatViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_config, container, false)
        val textView: TextView = root.findViewById(R.id.text_slideshow)
        chatViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}