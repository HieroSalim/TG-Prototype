package com.example.domicilio.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domicilio.R
import com.example.domicilio.view.adapters.viewholder.MedicViewHolder
import com.example.domicilio.services.listener.AppointmentListener
import com.example.domicilio.services.model.DoctorModel
import com.google.gson.internal.LinkedTreeMap




class MedicAdapter : RecyclerView.Adapter<MedicViewHolder>() {

    private var mMedicList: List<DoctorModel> = arrayListOf()
    private lateinit var mListener: AppointmentListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicViewHolder {
        val medic = LayoutInflater.from(parent.context).inflate(R.layout.doctor_item, parent,false)
        return MedicViewHolder(medic, mListener)
    }

    override fun onBindViewHolder(holder: MedicViewHolder, position: Int) {
        holder.bind(mMedicList[position] as LinkedTreeMap<String, DoctorModel>)
    }

    override fun getItemCount(): Int {
        return mMedicList.size
    }

    fun attachListener(listener: AppointmentListener) {
        mListener = listener
    }

    fun updateMedics(list: List<DoctorModel>){
        mMedicList = list
        notifyDataSetChanged()
    }
}