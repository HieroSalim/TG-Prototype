package com.example.domicilio.view.adapters.viewholder

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domicilio.R
import com.example.domicilio.control.AppointmentRepository
import com.example.domicilio.services.model.AppointmentModel
import com.example.domicilio.services.repository.local.SecurityPreferences
import com.google.gson.internal.LinkedTreeMap

class SolicitationViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val mAppointmentRepository = AppointmentRepository()
    private lateinit var mSecurityPreferences : SecurityPreferences

    fun bind(medic: LinkedTreeMap<String, AppointmentModel>){
        val requester = itemView.findViewById<TextView>(R.id.requester)
        val dateHourAppointment = itemView.findViewById<TextView>(R.id.appointment_date)
        val address = itemView.findViewById<TextView>(R.id.appointment_address)
        val accept = itemView.findViewById<Button>(R.id.button_accept)
        val refuse = itemView.findViewById<Button>(R.id.button_refuse)
        mSecurityPreferences = SecurityPreferences(itemView.context)

        val name = "Solicitante: ${medic["name"].toString()}"
        requester.text = name

        val dateTime = medic["dateHour"].toString().split(' ')
        val formatado = "${dateTime[0]} às ${dateTime[1]}"
        dateHourAppointment.text = formatado

        val street = medic["street"].toString()
        address.text = street

        accept.setOnClickListener {
            val token = mSecurityPreferences.get("token")
            mAppointmentRepository.accept(itemView.context,"Bearer $token", medic["idAppointment"].toString().toInt()
                , medic["idDoctor"].toString().toInt(), 1)
        }

        refuse.setOnClickListener {
            val token = mSecurityPreferences.get("token")
            mAppointmentRepository.accept(itemView.context,"Bearer $token", medic["idAppointment"].toString().toInt()
                , medic["idDoctor"].toString().toInt(), 0)
        }
    }

}