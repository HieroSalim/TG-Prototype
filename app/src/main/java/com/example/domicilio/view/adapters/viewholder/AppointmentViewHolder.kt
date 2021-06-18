package com.example.domicilio.view.adapters.viewholder

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.ImageView
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
import com.google.gson.internal.LinkedTreeMap
import java.text.SimpleDateFormat
import java.util.*

class AppointmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    private lateinit var mSecurityPreferences: SecurityPreferences
    private val mAppointmentRepository = AppointmentRepository()

    fun bind(medic: LinkedTreeMap<String, AppointmentModel>){
        val doctor_name = itemView.findViewById<TextView>(R.id.doctor_name)
        val dateHourAppointment = itemView.findViewById<TextView>(R.id.appointment_date)
        val status = itemView.findViewById<TextView>(R.id.appointment_status)
        val image = itemView.findViewById<ImageView>(R.id.doctor_image)

        mSecurityPreferences = SecurityPreferences(itemView.context)
        val type =  mSecurityPreferences.get("typeUser")
        val name = "Agendamento com: "+ if (medic["statusDoctor"].toString().split('.')[0].toInt() == 0) {
            medic["nameDoctor"].toString()
        }else if (medic["nameDoctor"].toString().equals(mSecurityPreferences.get("name"))) {
            medic["nameClient"].toString()
        } else {
            medic["nameDoctor"].toString()
        }
        doctor_name.text = name

        val dateTime = medic["dateHour"].toString().split(' ')
        val formatado = "${dateTime[0]} às ${dateTime[1]}"
        dateHourAppointment.text = formatado
        val statusFormat = "Status: "+ if (medic["statusDoctor"].toString().split('.')[0].toInt() == 0) "Em Espera" else "Confirmada"
        status.text = statusFormat

        val button = itemView.findViewById<Button>(R.id.button_changeConsult)

        if (medic["statusDoctor"].toString().split('.')[0].toInt() == 1
            && medic["nameDoctor"].toString() == mSecurityPreferences.get("name")
            && medic["action"].toString() == "Começar"
        ) {
            image.visibility = View.GONE
            button.visibility = View.VISIBLE
            button.text = medic["action"].toString()
        }else if(
            medic["statusDoctor"].toString().split('.')[0].toInt() == 1
            && medic["nameClient"].toString() == mSecurityPreferences.get("name")
            && medic["action"].toString() == "Terminar"
        ){
            image.visibility = View.GONE
            button.visibility = View.VISIBLE
            button.text = medic["action"].toString()
        }

        button.setOnClickListener {
            if(button.text.equals("Começar")){
                val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                val currentDate = sdf.format(Date())
                mAppointmentRepository.startConsult(mSecurityPreferences.get("token"),
                    medic["idAppointment"].toString().split('.')[0].toInt(),
                    medic["idDoctor"].toString().split('.')[0].toInt(),
                    currentDate,
                    object :APIListener<MessageModel>{
                        override fun onSuccess(result: MessageModel) {
                            Toast.makeText(itemView.context, result.msg, Toast.LENGTH_SHORT).show()
                            itemView.context.applicationContext.startActivity(Intent(itemView.context, MainActivity::class.java))
                        }

                        override fun onFailure(str: String) {
                            Toast.makeText(itemView.context, str, Toast.LENGTH_SHORT).show()
                        }

                    }
                )
            }else{
                val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                val currentDate = sdf.format(Date())
                mAppointmentRepository.finish(
                    mSecurityPreferences.get("token"),
                    medic["idAppointment"].toString().split('.')[0].toInt(),
                    currentDate, object : APIListener<MessageModel> {
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
}
