package com.example.domicilio.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domicilio.R
import com.example.domicilio.services.model.DoctorModel

class Doctor_Adapter {

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

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view: View = LayoutInflater.from(mContext).inflate(R.layout.doctor_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val doctor: DoctorModel = mDoctor[position]
            holder.doctor_name.text = doctor.name

            holder.doctor_specialization.text = doctor.specialization

        }

        override fun getItemCount(): Int {
            return mDoctor.size
        }
    }
}