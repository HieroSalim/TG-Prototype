package com.example.domicilio.services.adapters.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domicilio.R
import com.example.domicilio.services.listener.AppointmentListener
import com.example.domicilio.services.model.DoctorModel
import com.google.gson.internal.LinkedTreeMap

class MedicViewHolder(itemView: View, val listener: AppointmentListener) : RecyclerView.ViewHolder(itemView) {

    fun bind(medic: LinkedTreeMap<String, DoctorModel>){
        val doctorName = itemView.findViewById<TextView>(R.id.doctor_name)
        val doctorSpecialization = itemView.findViewById<TextView>(R.id.doctor_specialization)
        val doctorImage = itemView.findViewById<ImageView>(R.id.doctor_image)
        doctorName.text = medic["name"].toString()
        doctorSpecialization.text = medic["specialization"].toString()

        doctorImage.setOnClickListener{
            listener.onOpenProfile(medic["idProfile"].toString().toDouble().toInt())
        }
    }

}