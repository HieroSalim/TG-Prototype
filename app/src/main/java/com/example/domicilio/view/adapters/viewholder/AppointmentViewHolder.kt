package com.example.domicilio.view.adapters.viewholder

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domicilio.R
import com.example.domicilio.services.model.AppointmentModel
import com.example.domicilio.services.repository.local.SecurityPreferences
import com.google.gson.internal.LinkedTreeMap
import java.text.SimpleDateFormat
import java.util.*

class AppointmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    private lateinit var mSecurityPreferences: SecurityPreferences

    fun bind(medic: LinkedTreeMap<String, AppointmentModel>){
        val doctor_name = itemView.findViewById<TextView>(R.id.doctor_name)
        val dateHourAppointment = itemView.findViewById<TextView>(R.id.appointment_date)
        val status = itemView.findViewById<TextView>(R.id.appointment_status)

        mSecurityPreferences = SecurityPreferences(itemView.context)
        val name = "Agendamento com: ${medic["name"].toString()}"
        doctor_name.text = name

        val dateTime = medic["dateHour"].toString().split(' ')
        val formatado = "${dateTime[0]} às ${dateTime[1]}"
        dateHourAppointment.text = formatado
        val statusFormat = "Status: "+ if (medic["statusDoctor"].toString().split('.')[0].toInt() == 0) "Em Espera" else "Confirmada"
        status.text = statusFormat

        val button = itemView.findViewById<Button>(R.id.button_changeConsult)
        val type =  mSecurityPreferences.get("typeUser")
        if (medic["statusDoctor"].toString().split('.')[0].toInt() == 1 && type == "Médico") {
            button.visibility = View.VISIBLE
        }
    }
}
