package com.example.domicilio.view.adapters.viewholder

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.domicilio.R
import com.example.domicilio.control.AppointmentRepository
import com.example.domicilio.services.listener.APIListener
import com.example.domicilio.services.model.AppointmentModel
import com.example.domicilio.services.model.MessageModel
import com.example.domicilio.services.repository.local.SecurityPreferences
import com.example.domicilio.view.MainActivity
import com.example.domicilio.view.adapters.Solicitation_Adapter
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
            mAppointmentRepository.accept(itemView.context,"Bearer $token", medic["idAppointment"].toString().split('.')[0].toInt()
                , medic["idDoctor"].toString().split('.')[0].toInt(), 1)
            itemView.context.applicationContext.startActivity(Intent(itemView.context, MainActivity::class.java))
        }

        refuse.setOnClickListener {
            val token = mSecurityPreferences.get("token")

            mAppointmentRepository.refuse("Bearer $token", medic["idAppointment"].toString().split('.')[0].toInt()
                , object : APIListener<MessageModel>{
                    override fun onSuccess(result: MessageModel) {
                        Toast.makeText(itemView.context, result.msg, Toast.LENGTH_SHORT).show()
                        itemView.context.applicationContext.startActivity(Intent(itemView.context, MainActivity::class.java))
                    }

                    override fun onFailure(str: String) {
                        Toast.makeText(itemView.context, str, Toast.LENGTH_SHORT).show()
                    }

                })
        }
    }

}