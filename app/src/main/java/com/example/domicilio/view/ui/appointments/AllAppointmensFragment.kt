package com.example.domicilio.view.ui.appointments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.domicilio.R

class AllAppointmensFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_all_appointments, container, false)
        val textView: TextView = root.findViewById(R.id.text_gallery)
        textView.text = "Listagem de Agendamentos"
        return root
    }

    fun createConsult(){

    }
    fun selectDoctors(){

    }
    fun selectSymptoms(){

    }
    fun verifyDateHour(){

    }
    fun selectDateHour(){

    }

}