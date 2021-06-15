package com.example.domicilio.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domicilio.R
import com.example.domicilio.services.model.AppointmentModel
import com.example.domicilio.view.adapters.viewholder.SolicitationViewHolder
import com.google.gson.internal.LinkedTreeMap

class Solicitation_Adapter : RecyclerView.Adapter<SolicitationViewHolder>(){
    private var mAppointmentList: List<AppointmentModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolicitationViewHolder {
        val appointment = LayoutInflater.from(parent.context).inflate(R.layout.appointment_item, parent, false)
        return SolicitationViewHolder(appointment)
    }

    override fun onBindViewHolder(holder: SolicitationViewHolder, position: Int) {
        holder.bind(mAppointmentList[position] as LinkedTreeMap<String, AppointmentModel>)
    }

    override fun getItemCount(): Int {
        return mAppointmentList.count()
    }

    fun updateAppointment(list: List<AppointmentModel>){
        mAppointmentList = list
    }
}