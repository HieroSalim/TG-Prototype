package com.example.domicilio.control

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domicilio.R
import com.example.domicilio.services.model.AppointmentModel
import com.example.domicilio.services.model.DoctorModel

class Doctor_Adapter: RecyclerView.Adapter<Doctor_Adapter.ViewHolder> {
    var mContext: Context
    var mDoctor: MutableList<DoctorModel>

    constructor(mContext: Context, mDoctor: MutableList<DoctorModel>) {
        this.mContext = mContext
        this.mDoctor = mDoctor

    }

    class ViewHolder: RecyclerView.ViewHolder{
        constructor(itemView: View): super(itemView){
            this.doctor_name = doctor_name
            this.doctor_specialization = doctor_specialization
            this.doctor_image = doctor_image
        }
        var doctor_name: TextView = itemView.findViewById(R.id.doctor_name)
        var doctor_specialization: TextView = itemView.findViewById(R.id.doctor_specialization)
        var doctor_image: ImageView = itemView.findViewById(R.id.doctor_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Doctor_Adapter.ViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.doctor_item, parent, false)
        return Doctor_Adapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: Doctor_Adapter.ViewHolder, position: Int) {
        val doctor: DoctorModel = mDoctor[position]
        holder.doctor_name.text = doctor.name

        holder.doctor_specialization.text = doctor.specialization

    }

    override fun getItemCount(): Int {
        return mDoctor.size
    }
}
