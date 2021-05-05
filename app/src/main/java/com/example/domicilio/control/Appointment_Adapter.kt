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
import com.example.domicilio.services.model.UserChatModel
import kotlinx.android.synthetic.main.appointment_item.view.*

class Appointment_Adapter: RecyclerView.Adapter<Appointment_Adapter.ViewHolder> {
    var mContext: Context
    var mAppointment: MutableList<AppointmentModel>

    constructor(mContext: Context, mAppointment: MutableList<AppointmentModel>) {
        this.mContext = mContext
        this.mAppointment = mAppointment

    }

    class ViewHolder: RecyclerView.ViewHolder{
        constructor(itemView: View): super(itemView){
            this.doctor_name = doctor_name
            this.appointment_date = appointment_date
            this.appointment_status = appointment_status
            this.doctor_image = doctor_image
        }
        var doctor_name: TextView = itemView.findViewById(R.id.doctor_name)
        var appointment_date: TextView = itemView.findViewById(R.id.appointment_date)
        var appointment_status: TextView = itemView.findViewById(R.id.appointment_status)
        var doctor_image: ImageView = itemView.findViewById(R.id.doctor_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Appointment_Adapter.ViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.appointment_item, parent, false)
        return Appointment_Adapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: Appointment_Adapter.ViewHolder, position: Int) {
        val appointment: AppointmentModel = mAppointment[position]
        holder.doctor_name.text = appointment.doctors

        holder.appointment_date.text = appointment.dateHour

        holder.appointment_status.text = appointment.status

    }

    override fun getItemCount(): Int {
        return mAppointment.size
    }
}